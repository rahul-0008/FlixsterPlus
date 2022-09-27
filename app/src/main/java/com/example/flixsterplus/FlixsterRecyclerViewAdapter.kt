package com.example.flixsterplus

import android.os.TestLooperManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FlixsterRecyclerViewAdapter(private val movies : List<MovieItem>,
                                  private val mListener: OnListFragmentInteractionListener) : RecyclerView.Adapter<FlixsterRecyclerViewAdapter.MovieHolder>()
{
                                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
                                        val view = LayoutInflater.from(parent.context)
                                            .inflate(R.layout.fragment_movie, parent, false)
                                        return MovieHolder(view) }

                                  inner class  MovieHolder(val mView: View) : RecyclerView.ViewHolder(mView){
                                      var mItem : MovieItem ? =null
                                      val mMovieDescription: TextView = mView.findViewById<TextView>(R.id.movie_description)
                                      val mMovieImage: ImageView = mView.findViewById<ImageView>(R.id.movie_image)
                                      val mMovieName : TextView = mView.findViewById<TextView>(R.id.movie_name)

                                  }

                                override fun onBindViewHolder(holder: MovieHolder, position: Int) {
                                    val movie = movies[position]
                                    holder.mMovieName.text = movie.movieName
                                    holder.mMovieDescription.text = movie.description

                                    Glide.with(holder.mView)
                                        .load(movie.movieImageUrl)
                                        .centerInside()
                                        .into(holder.mMovieImage)
                                    holder.mView.setOnClickListener {
                                        holder.mItem?.let { book ->
                                            mListener?.onItemClick(book)
                                        }
                                    }
                                }

                                override fun getItemCount(): Int {
                                    return movies.size
                                }
}