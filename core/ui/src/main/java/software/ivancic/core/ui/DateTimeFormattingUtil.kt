package software.ivancic.core.ui

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateTimeFormattingUtil {
    fun formatDate(dateTime: Long): String {
        val zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.of("UTC"))
        return zonedDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }

    fun formatTime(dateTime: Long): String {
        val zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.of("UTC"))
        return zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    fun formatDateTime(dateTime: Long): String {
        return "${formatDate(dateTime)} ${formatTime(dateTime)}"
    }
}
