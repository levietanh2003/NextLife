
fun formatCurrencyFromString(input: String?): String {
    // Kiểm tra nếu đầu vào là null hoặc rỗng
    if (input.isNullOrEmpty()) {
        return "0 VND"
    }

    // Cố gắng chuyển đổi chuỗi thành số
    val number = input.toDoubleOrNull() ?: return "0 đ"

    // Nhân giá trị với 1000
    val value = number * 1000

    // Định dạng theo đơn vị tiền tệ
    val formattedValue = String.format("%,.0f", value)

    return "$formattedValue đ/tháng"
}
