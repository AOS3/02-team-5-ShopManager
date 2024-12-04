package com.lion.five.shopmanager.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/searchMovieList.json") // JSON 응답 엔드포인트
    suspend fun getMovieList(
        @Query("key") apiKey: String = "e5c561a2c12c4a7329cf89b803bd9529", // API 키
        @Query("movieNm") movieName: String // 검색할 영화 이름
    ): MovieInfoResponse
}

