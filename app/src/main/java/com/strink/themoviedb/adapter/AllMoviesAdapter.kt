package com.strink.themoviedb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.squareup.picasso.Picasso
import com.strink.themoviedb.coroutine.CoroutineAsyncTask
import com.strink.themoviedb.R
import com.strink.themoviedb.database.MovieDatabase
import com.strink.themoviedb.database.MovieEntity
import com.strink.themoviedb.model.Movies

class AllMoviesAdapter(private var movies: ArrayList<Movies>, private val context: Context) :
    RecyclerView.Adapter<AllMoviesAdapter.AllMoviesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllMoviesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.movies_custom_row, parent, false)
        return AllMoviesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AllMoviesViewHolder, position: Int) {
        val resObject = movies[position]
        holder.movieThumbnail.clipToOutline = true
        holder.movieName.text = resObject.name
        holder.movieRating.text = resObject.rating
        Picasso.get().load("https://image.tmdb.org/t/p/original" + resObject.imageUrl)
            .error(R.drawable.download).into(holder.movieThumbnail)

        val listOfFavorites = GetAllFavTask(context).doInBackground()

        if (listOfFavorites.isNotEmpty() && listOfFavorites.contains(resObject.id.toString())) {
            holder.favImage.setImageResource(R.drawable.heart_red)
        } else {
            holder.favImage.setImageResource(R.drawable.heart)
        }

        holder.favImage.setOnClickListener {
            val movieEntity = MovieEntity(
                resObject.id,
                resObject.name,
                resObject.rating,
                resObject.imageUrl
            )
            if (!DBTask(context, movieEntity, 1).doInBackground()) {
                val result = DBTask(context, movieEntity, 2).doInBackground()
                if (result) {
                    holder.favImage.setImageResource(R.drawable.heart_red)
                }
            } else {
                val result = DBTask(context, movieEntity, 3).doInBackground()
                if (result) {
                    holder.favImage.setImageResource(R.drawable.heart)
                }
            }
        }

        holder.cardMovie.setOnClickListener {
            Toast.makeText(context, "Clicked on: ${holder.movieName.text}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class AllMoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieThumbnail: ImageView = view.findViewById(R.id.imgMovieThumbnail)
        val movieName: TextView = view.findViewById(R.id.txtMovieName)
        val movieRating: TextView = view.findViewById(R.id.txtMovieRating)
        val favImage: ImageView = view.findViewById(R.id.imgIsFav)
        val cardMovie: CardView = view.findViewById(R.id.cardMovie)
    }

    class DBTask(context: Context, private val movieEntity: MovieEntity, private val mode: Int) :
        CoroutineAsyncTask<Void, Void, Boolean>() {
        private val db = Room.databaseBuilder(context, MovieDatabase::class.java, "movie-db").allowMainThreadQueries().build()

        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    val res: MovieEntity? = db.movieDao().getMovieById(movieEntity.id.toString())
                    db.close()
                    return res!=null
                }
                2 -> {
                    db.movieDao().insertMovie(movieEntity)
                    db.close()
                    return true
                }
                3 -> {
                    db.movieDao().deleteMovie(movieEntity)
                    db.close()
                    return true
                }
            }
            return false
        }
    }

    class GetAllFavTask(context: Context) :
        CoroutineAsyncTask<Void, Void, List<String>>() {
        private val db = Room.databaseBuilder(context, MovieDatabase::class.java, "movie-db").allowMainThreadQueries().build()
        override fun doInBackground(vararg params: Void?): List<String> {
            val list = db.movieDao().getAllMovies()
            val listOfIds = arrayListOf<String>()
            for (i in list) {
                listOfIds.add(i.id.toString())
            }
            return listOfIds
        }
    }
}