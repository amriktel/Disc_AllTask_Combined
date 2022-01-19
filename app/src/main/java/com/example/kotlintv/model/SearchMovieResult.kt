package com.example.example

import com.google.gson.annotations.SerializedName


data class SearchMovieResult (

  @SerializedName("page"          ) var page         : Int?          = null,
  @SerializedName("results"       ) var results      : List<Results> = arrayListOf(),
  @SerializedName("total_pages"   ) var totalPages   : Int?          = null,
  @SerializedName("total_results" ) var totalResults : Int?          = null

)