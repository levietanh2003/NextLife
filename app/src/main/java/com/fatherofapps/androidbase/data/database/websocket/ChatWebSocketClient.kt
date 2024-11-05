package com.fatherofapps.androidbase.data.database.websocket

import android.util.Log
import com.fatherofapps.androidbase.data.models.Message
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.http.takeFrom
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ChatWebSocketClient(
    private val serverUrl: String = "wss://echo.websocket.events" // WebSocket server miễn phí
) {
    private val client = HttpClient {
        install(WebSockets)
    }
    private var socketSession: WebSocketSession? = null
    private val _incomingMessages = MutableSharedFlow<Message>(replay = 0)
    val incomingMessages = _incomingMessages.asSharedFlow()

    suspend fun connect() {
        Log.d("ChatWebSocketClient", "Attempting to connect to WebSocket server at $serverUrl")
        try {
            socketSession = client.webSocketSession { url.takeFrom(serverUrl) }
            Log.d("ChatWebSocketClient", "Connected to WebSocket server")
            receiveMessages() // Gọi hàm nhận tin nhắn sau khi kết nối thành công
        } catch (e: Exception) {
            Log.e("ChatWebSocketClient", "Connection failed: ${e.message}")
            e.printStackTrace()
        }
    }

    internal suspend fun receiveMessages() {
        Log.d("ChatWebSocketClient", "Listening for incoming messages")
        socketSession?.incoming?.consumeEach { frame ->
            when (frame) {
                is Frame.Text -> {
                    val message = frame.readText()
                    Log.d("ChatWebSocketClient", "Received message: $message")
                    _incomingMessages.emit(Message("Server", message, System.currentTimeMillis()))
                }
                is Frame.Close -> {
                    Log.d("ChatWebSocketClient", "WebSocket closed by server")
                    disconnect()
                }
                // Có thể xử lý thêm các frame khác nếu cần
            }
        }
    }

    suspend fun sendMessage(message: Message) {
        Log.d("ChatWebSocketClient", "Sending message: ${message.content}")
        socketSession?.send(Frame.Text(message.content))
    }

    suspend fun disconnect() {
        Log.d("ChatWebSocketClient", "Disconnecting from WebSocket server")
        socketSession?.close()
        socketSession = null
        Log.d("ChatWebSocketClient", "Disconnected from WebSocket server")
    }
}
