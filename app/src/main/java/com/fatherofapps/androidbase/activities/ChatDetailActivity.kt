package com.fatherofapps.androidbase.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatherofapps.androidbase.R
import com.fatherofapps.androidbase.adapter.MessagesAdapter
import com.fatherofapps.androidbase.data.database.MessageUser


import com.fatherofapps.androidbase.databinding.ActivityChatDetailBinding

class ChatDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatDetailBinding
    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var messagesAdapter: MessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView)
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Giả lập dữ liệu
        val messages = listOf(
            MessageUser("Xin chào!", "10:00", true),
            MessageUser("Chào bạn!", "10:01", false),
            MessageUser("Bạn có khỏe không?", "10:02", true),
            MessageUser("Tôi khỏe, cảm ơn!", "10:03", false)
        )

        messagesAdapter = MessagesAdapter(messages)
        messagesRecyclerView.adapter = messagesAdapter

    }
}