package com.example.practica.data.entity

import android.net.Uri
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromUriString(value: String?): Uri? {
        return value?.let { Uri.parse(it) }
    }

    @TypeConverter
    fun toUriString(uri: Uri?): String? {
        return uri.toString()
    }
}