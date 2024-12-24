package com.example.multiplatformcrash.data.mapper

import com.example.multiplatformcrash.data.remote.dto.IntradayInfoDto
import com.example.multiplatformcrash.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntradayInfoDto.toIntradayInfo():IntradayInfo{
    val pattern="yyyy-MM-dd HH:mm:ss"
    val formatter=DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val LocalDateTime=LocalDateTime.parse(timestamp,formatter)
    return IntradayInfo(
        date = LocalDateTime,
        close=close
    )

}