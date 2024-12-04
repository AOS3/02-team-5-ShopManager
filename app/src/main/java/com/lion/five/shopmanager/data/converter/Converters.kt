package com.lion.five.shopmanager.data.converter

import androidx.room.TypeConverter

class Converters {

    // List -> String 변환
    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    // String -> List 변환
    @TypeConverter
    fun toList(data: String?): List<String>? {
        return data?.split(",")
    }

}