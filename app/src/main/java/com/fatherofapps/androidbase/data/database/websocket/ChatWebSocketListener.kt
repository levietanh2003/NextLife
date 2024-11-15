package com.fatherofapps.androidbase.data.database.websocket

import android.util.Log
import com.fatherofapps.androidbase.activities.ChatDetailActivity
import okhttp3.*
import okio.ByteString
import javax.inject.Inject

class ChatWebSocketListener @Inject constructor() : WebSocketListener() {

    companion object {
        private const val TAG = "ChatWebSocketListener"
    }

    // Phương thức này được gọi khi WebSocket kết nối thành công
    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d(TAG, "WebSocket connected: $response")
        // Tại đây bạn chỉ cần ghi lại thông tin kết nối, không cần truyền dữ liệu về Activity
    }

    // Phương thức này được gọi khi nhận được một tin nhắn dạng text
    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d(TAG, "Received message: $text")
        // Bạn có thể xử lý tin nhắn ở đây nếu cần
    }

    // Phương thức này được gọi khi nhận được dữ liệu dạng byte
    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        Log.d(TAG, "Received bytes: ${bytes.hex()}")
    }

    // Phương thức này được gọi khi WebSocket đóng kết nối
    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d(TAG, "Closing WebSocket: $code / $reason")
        webSocket.close(code, reason)
    }

    // Phương thức này được gọi khi WebSocket đã đóng
    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d(TAG, "WebSocket closed: $code / $reason")
    }

    // Phương thức này được gọi khi có lỗi xảy ra
    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e(TAG, "Error: ${t.message}", t)
    }
}


//
//class ChatWebSocketListener @Inject constructor() : WebSocketListener() {
//
//    companion object {
//        private const val TAG = "ChatWebSocketListener"
//    }
//
//    override fun onOpen(webSocket: WebSocket, response: Response) {
//        // Kết nối thành công
//        Log.d(TAG, "WebSocket connected: $response")
//        // Gửi tin nhắn nếu cần sau khi mở kết nối
//        val greetingMessage = "{\"message\": \"Hello, WebSocket!\"}"
//        webSocket.send(greetingMessage)
//    }
//
//    override fun onMessage(webSocket: WebSocket, text: String) {
//        // Nhận được tin nhắn dạng text
//        Log.d(TAG, "Received message: $text")
//        // Xử lý tin nhắn nhận được (e.g., cập nhật UI, lưu vào database)
//    }
//
//    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//        // Nhận được tin nhắn dạng byte
//        Log.d(TAG, "Received bytes: ${bytes.hex()}")
//    }
//
//    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//        // Đóng kết nối từ phía server hoặc client
//        Log.d(TAG, "Closing WebSocket: $code / $reason")
//        webSocket.close(code, reason)
//    }
//
//    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
//        // Kết nối đã đóng hoàn toàn
//        Log.d(TAG, "WebSocket closed: $code / $reason")
//    }
//
//    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//        // Có lỗi xảy ra
//        Log.e(TAG, "Error: ${t.message}", t)
//        // Có thể thực hiện kết nối lại (reconnect) tại đây nếu cần thiết
//    }
//}
