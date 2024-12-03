package com.lion.five.shopmanager.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/"

    val apiService: MovieApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 응답을 처리할 변환기
            .build()
            .create(MovieApiService::class.java)
    }
}