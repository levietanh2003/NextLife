import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

@RequiresApi(Build.VERSION_CODES.O)
fun convertToDate(unixTimestamp: Double): String {
    // Chuyển đổi từ Unix Timestamp (giây) sang LocalDateTime
    val timestampInSeconds = unixTimestamp.toLong() // Lấy phần nguyên (bỏ số thập phân)
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestampInSeconds), ZoneId.systemDefault())

    // Định dạng ngày tháng năm
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return dateTime.format(formatter) // Trả về chuỗi ngày định dạng dd-MM-yyyy
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatBirthDate(birthDateArray: List<Int>): String {
    // Mảng birthDateArray có dạng [year, month, day]
    val year = birthDateArray[0]
    val month = birthDateArray[1]
    val day = birthDateArray[2]

    // Tạo đối tượng LocalDate từ year, month, day
    val date = LocalDate.of(year, month, day)

    // Định dạng ngày theo định dạng bạn muốn, ví dụ: "dd/MM/yyyy"
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // Chuyển đối tượng LocalDate thành chuỗi theo định dạng
    return date.format(formatter)
}
