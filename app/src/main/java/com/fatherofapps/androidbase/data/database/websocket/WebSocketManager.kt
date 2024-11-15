package com.fatherofapps.androidbase.data.database.websocket

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.HttpHeaders
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WebSocketManager {
    private val TAG = "WebSocketManager"

    private val client = HttpClient(CIO) {
        install(WebSockets) {
            pingInterval = 20_000 // Send ping every 20 seconds
            maxFrameSize = Long.MAX_VALUE
        }

        engine {
            // Disable SSL verification (not recommended for production)
//            sslContext = SSLContext.getInstance("TLS")
//            sslContext.init(null, null, null)
        }
    }

    // Updated WebSocket Echo Server URL
    private val socketUrl = "wss://echo.websocket.org/"
    private var webSocketJob: Job? = null

    // Flow for broadcasting messages to listeners
    private val _messageFlow = MutableSharedFlow<String>()
    val messageFlow = _messageFlow.asSharedFlow()

    // Connection status
    private var isConnected = false

    fun connectWebSocket(authToken: String? = null) {
        if (isConnected) {
            Log.d(TAG, "WebSocket is already connected")
            return
        }

        webSocketJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                client.webSocket(
                    urlString = socketUrl
                ) {
                    // Configure headers (if needed)
                    request {
                        header(HttpHeaders.Connection, "Upgrade")
                        header(HttpHeaders.Upgrade, "websocket")
                        authToken?.let {
                            header(HttpHeaders.Authorization, "Bearer $it")
                        }
                    }

                    isConnected = true
                    Log.d(TAG, "Connected to WebSocket")

                    try {
                        // Handle incoming messages
                        for (frame in incoming) {
                            when (frame) {
                                is Frame.Text -> {
                                    val message = frame.readText()
                                    withContext(Dispatchers.Main) {
                                        Log.d(TAG, "Message received: $message")
                                        _messageFlow.emit(message)
                                    }
                                }
                                is Frame.Binary -> {
                                    Log.d(TAG, "Binary frame received")
                                }
                                is Frame.Ping -> {
                                    Log.d(TAG, "Ping frame received")
                                    send(Frame.Pong("".toByteArray()))
                                }
                                is Frame.Close -> {
                                    Log.d(TAG, "Close frame received")
                                    isConnected = false
                                    break
                                }
                                else -> Log.d(TAG, "Unknown frame received: ${frame.frameType}")
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error in WebSocket message loop: ${e.message}")
                        isConnected = false
                        throw e
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error connecting WebSocket: ${e.message}")
                isConnected = false
                // Implement retry logic here if needed
                retryConnection(authToken)
            }
        }
    }

    private suspend fun retryConnection(authToken: String? = null, maxRetries: Int = 3) {
        var retryCount = 0
        while (retryCount < maxRetries && !isConnected) {
            try {
                Log.d(TAG, "Attempting to reconnect... Attempt ${retryCount + 1}/$maxRetries")
                kotlinx.coroutines.delay(5000) // Wait 5 seconds between retries
                connectWebSocket(authToken)
                retryCount++
            } catch (e: Exception) {
                Log.e(TAG, "Retry attempt failed: ${e.message}")
            }
        }
    }

    suspend fun sendMessage(message: String) {
        try {
            client.webSocket(socketUrl) {
                send(Frame.Text(message))
                Log.d(TAG, "Message sent: $message")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error sending message: ${e.message}")
        }
    }

    fun disconnectWebSocket() {
        webSocketJob?.cancel()
        webSocketJob = null
        isConnected = false
        client.close()
        Log.d(TAG, "WebSocket disconnected")
    }

    // Check if WebSocket is currently connected
    fun isConnected() = isConnected
}
