package com.fatherofapps.androidbase.activities

import androidx.lifecycle.viewModelScope
import com.fatherofapps.androidbase.base.viewmodel.BaseViewModel
import com.fatherofapps.androidbase.data.database.websocket.ChatWebSocketClient
import com.fatherofapps.androidbase.data.models.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : BaseViewModel() {
    private val chatWebSocketClient = ChatWebSocketClient()
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

//    init {
//        connectWebSocket()
//    }

//    private fun connectWebSocket() {
//        viewModelScope.launch {
//            chatWebSocketClient.connect()
//            chatWebSocketClient.receiveMessages()
//            chatWebSocketClient.incomingMessages.collect { message ->
//                _messages.value = _messages.value + message
//            }
//        }
//    }

//    fun sendMessage(content: String) {
//        val message = Message("User", content, System.currentTimeMillis())
//        viewModelScope.launch {
//            chatWebSocketClient.sendMessage(message)
//            _messages.value = _messages.value + message
//        }
//    }
//
//    override fun onCleared() {
//        viewModelScope.launch {
//            chatWebSocketClient.disconnect()
//        }
//        super.onCleared()
//    }
}