package com.fatherofapps.androidbase.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.adapter.MessagesAdapter
import com.fatherofapps.androidbase.data.database.MessageUser
import com.fatherofapps.androidbase.data.database.websocket.WebSocketManager
import com.fatherofapps.androidbase.databinding.ActivityChatDetailBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatDetailBinding
    private lateinit var messagesAdapter: MessagesAdapter
    private val webSocketManager = WebSocketManager()
    private val messages = mutableListOf<MessageUser>()
    private val TAG = "ChatDetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupWebSocket()
        setupMessageSending()
    }

    private fun setupRecyclerView() {
        // Add initial test messages
        messages.addAll(listOf(
            MessageUser(
                text = "Xin chào!",
                timestamp = getCurrentTime(),
                isSent = true
            ),
            MessageUser(
                text = "Chào bạn!",
                timestamp = getCurrentTime(),
                isSent = false
            ),
            MessageUser(
                text = "Bạn có khỏe không?",
                timestamp = getCurrentTime(),
                isSent = true
            ),
            MessageUser(
                text = "Tôi khỏe, cảm ơn!",
                timestamp = getCurrentTime(),
                isSent = false
            )
        ))

        messagesAdapter = MessagesAdapter(messages)
        binding.messagesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatDetailActivity).apply {
                stackFromEnd = true  // Messages start from bottom
            }
            adapter = messagesAdapter
        }
    }

    private fun setupWebSocket() {
        // Start WebSocket connection
        webSocketManager.connectWebSocket()

        // Listen for incoming messages
        lifecycleScope.launch {
            webSocketManager.messageFlow.collect { message ->
                // Add received message to the list
                val newMessage = MessageUser(
                    text = message,
                    timestamp = getCurrentTime(),
                    isSent = false
                )
                messages.add(newMessage)
                runOnUiThread {
                    messagesAdapter.notifyItemInserted(messages.size - 1)
                    binding.messagesRecyclerView.scrollToPosition(messages.size - 1)
                }
            }
        }

        // Monitor connection status
        lifecycleScope.launch {
            try {
                if (webSocketManager.isConnected()) {
                    Log.d(TAG, "WebSocket connected successfully")
                    showToast("Connected to chat server")
                } else {
                    Log.e(TAG, "WebSocket connection failed")
                    showToast("Failed to connect to chat server")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error monitoring WebSocket: ${e.message}")
                showToast("Error connecting to chat server")
            }
        }
    }

    private fun setupMessageSending() {
        binding.btnChat.setOnClickListener {
            val messageText = binding.messageInput.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
                binding.messageInput.text.clear()
            }
        }
    }

    private fun sendMessage(messageText: String) {
        lifecycleScope.launch {
            try {
                // Send message through WebSocket
                webSocketManager.sendMessage(messageText)

                // Add message to local list
                val newMessage = MessageUser(
                    text = messageText,
                    timestamp = getCurrentTime(),
                    isSent = true
                )
                messages.add(newMessage)
                messagesAdapter.notifyItemInserted(messages.size - 1)
                binding.messagesRecyclerView.scrollToPosition(messages.size - 1)

                Log.d(TAG, "Message sent successfully: $messageText")
            } catch (e: Exception) {
                Log.e(TAG, "Error sending message: ${e.message}")
                showToast("Failed to send message")
            }
        }
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketManager.disconnectWebSocket()
    }
}