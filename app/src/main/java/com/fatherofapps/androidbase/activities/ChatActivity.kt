package com.fatherofapps.androidbase.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.fatherofapps.androidbase.adapter.ChatAdapter
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.databinding.ActivityChatBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatActivity : BaseActivity() {
    private lateinit var binding: ActivityChatBinding
    private val chatViewModel: ChatViewModel by viewModels()
    private val chatAdapter = ChatAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("ChatActivity", "ChatActivity created") // Log khi ChatActivity khởi tạo

        setupRecyclerView()
        setupObservers()
        setupSendButton()

        binding.btnMore.setOnClickListener {
            // Khởi chạy ChatDetailsActivity
            val intent = Intent(this, ChatDetailActivity::class.java)
            startActivity(intent)
            Log.d("ChatActivity", "Navigating to ChatDetailsActivity") // Log khi chuyển sang ChatDetailsActivity
        }
    }

    private fun setupRecyclerView() {
//        binding.recyclerView.apply {
//            adapter = chatAdapter
//            layoutManager = LinearLayoutManager(this@ChatActivity)
//        }
//        Log.d("ChatActivity", "RecyclerView setup complete")
    // Log khi RecyclerView đã thiết lập xong
    }

    private fun setupObservers() {
//        lifecycleScope.launch {
//            chatViewModel.messages.collect { messages ->
//                Log.d("ChatActivity", "Messages collected: ${messages.size}") // Log khi nhận danh sách tin nhắn
//                chatAdapter.submitList(messages)
//                binding.recyclerView.scrollToPosition(messages.size - 1)
//            }
//        }
    }

    private fun setupSendButton() {
//        binding.sendButton.setOnClickListener {
//            val messageContent = binding.messageInput.text.toString()
//            if (messageContent.isNotBlank()) {
//                Log.d("ChatActivity", "Sending message: $messageContent") // Log khi nhấn gửi tin nhắn
//                chatViewModel.sendMessage(messageContent)
//                binding.messageInput.text.clear()
//            } else {
//                Log.d("ChatActivity", "Message content is blank") // Log nếu nội dung tin nhắn rỗng
//            }
//        }
    }
}
