package com.example.flixsterplus

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

class MovieItem(title: String,poster_path : String, overview: String) {
    @SerializedName("title")
    var movieName = title
    @SerializedName("poster_path")
    var movieImageUrl = "https://image.tmdb.org/t/p/w342/$poster_path"
    @SerializedName("overview")
    var description = overview

}