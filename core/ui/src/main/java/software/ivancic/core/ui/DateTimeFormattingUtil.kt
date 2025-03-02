package software.ivancic.core.ui

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateTimeFormattingUtil {
    fun formatDate(dateTime: Long): String {
        val zonedDateTime = Instant.ofEpochMilli(dateTime).atZone(ZoneId.systemDefault())
        return zonedDateTime.format(DateTimeFormatter.ISO_DATE)
    }

    fun formatTime(dateTime: Long): String {
        val zonedDateTime = Instant.ofEpochMilli(dateTime).atZone(ZoneId.systemDefault())
        return zonedDateTime.format(DateTimeFormatter.ISO_TIME)
    }
}
