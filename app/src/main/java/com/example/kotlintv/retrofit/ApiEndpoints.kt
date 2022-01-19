package com.example.kotlintv.retrofit

import com.example.example.SearchMovieResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

 interface ApiEndpoints {

    @GET("/3/search/movie/")
    fun getMovies(@Query("api_key") key: String,
                @Query("language") language:String ,
                @Query("query") query: String ,
                @Query("page") page: Int,
                @Query("include_adult") includeAdult: Boolean=false) : Call<SearchMovieResult>


  //  https://api.themoviedb.org/3/search/movie?api_key=fee68e44aa6c118c8eee47f3b9cf04fa&language=en-US&query=avat&page=1&include_adult=false

}