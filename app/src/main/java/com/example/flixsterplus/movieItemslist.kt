package com.example.flixsterplus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONObject
import org.json.JSONArray

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MovieItemsList : Fragment(), OnListFragmentInteractionListener{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list,container,false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = this.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView){
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()

        client[
                "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
                object: JsonHttpResponseHandler()

                {

                    override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {

                        progressBar.hide()
                        val models: List<MovieItem> = ArrayList()
                        recyclerView.adapter = FlixsterRecyclerViewAdapter(models, this@MovieItemsList)
                        Log.d("MovieItemsList", "response successful")
                        val resultsJSON  = json.jsonObject.getJSONArray("results")

                        val parsed = parseFromJason(resultsJSON)
                        val models1 : List<MovieItem> = parsed
                        recyclerView.adapter = FlixsterRecyclerViewAdapter(models1,this@MovieItemsList)

                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        progressBar.hide()

                        t?.message?.let {
                            Log.e("MovieItems",errorResponse)
                        }
                    }

                }
        ]

    }
    fun parseFromJason(movieJasonArray: JSONArray): List<MovieItem> {
        val movies = mutableListOf<MovieItem>()
        for (i in 0 until movieJasonArray.length()) {
            val movieJson = movieJasonArray.getJSONObject(i)
            movies.add(
                MovieItem(
                    movieJson.getString("title"),
                    movieJson.getString("poster_path"),
                    movieJson.getString("overview")
                )
            )
        }
        return movies
    }
    override fun onItemClick(item: MovieItem) {
        Toast.makeText(context, "test: " + item.movieName, Toast.LENGTH_LONG).show()
    }
}