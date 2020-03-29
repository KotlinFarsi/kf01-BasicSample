package com.kotlinfarsi.basicsample.db.converter

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @JvmStatic
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @JvmStatic
    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
