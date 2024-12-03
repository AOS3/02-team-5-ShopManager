package com.lion.five.shopmanager.retrofit

data class MovieInfoResponse(
    val movieListResult: MovieListResult
)

data class MovieListResult(
    val movieList: List<MovieDetail>
)

data class MovieDetail(
    val movieNm: String, // 영화 이름
    val directors: List<Director>,
    val openDt: String, // 개봉년도
    val genreAlt: String // 장르
)

data class Director(
    val directorNm: String // 감독 이름
)