package com.lion.five.shopmanager.data.converter

import androidx.room.TypeConverter
import com.lion.five.shopmanager.data.MovieName

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

    // Enum -> String 변환
    @TypeConverter
    fun fromMovieName(movieName: MovieName?): String? {
        return movieName?.movieName
    }

    // String -> Enum 변환
    @TypeConverter
    fun toMovieName(value: String?): MovieName {
        return MovieName.values().find { it.movieName == value } ?: MovieName.UNKNOWN
    }
}