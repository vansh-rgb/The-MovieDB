package com.strink.themoviedb.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.strink.themoviedb.coroutine.CoroutineAsyncTask
import com.strink.themoviedb.R
import com.strink.themoviedb.adapter.AllMoviesAdapter
import com.strink.themoviedb.database.MovieDatabase
import com.strink.themoviedb.database.MovieEntity
import com.strink.themoviedb.model.Movies


class FavoritesFragment : Fragment() {

    private lateinit var recyclerMovies: RecyclerView
    private lateinit var allMoviesAdapter: AllMoviesAdapter
    private var moviesList = arrayListOf<Movies>()
    private lateinit var rlLoading: RelativeLayout
    private lateinit var rlFav: RelativeLayout
    private lateinit var rlNoFav: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        rlFav = view.findViewById(R.id.rlFavorites)
        rlNoFav = view.findViewById(R.id.rlNoFavorites)
        rlLoading = view.findViewById(R.id.rlLoading)
        rlLoading.visibility = View.VISIBLE
        setUpRecycler(view)
        return view
    }

    private fun setUpRecycler(view: View) {
        recyclerMovies = view.findViewById(R.id.recyclerMovies)

        val backgroundList = FavoritesAsync(activity as Context).doInBackground()
        if (backgroundList.isEmpty()) {
            rlLoading.visibility = View.GONE
            rlFav.visibility = View.GONE
            rlNoFav.visibility = View.VISIBLE
        } else {
            rlFav.visibility = View.VISIBLE
            rlLoading.visibility = View.GONE
            rlNoFav.visibility = View.GONE
            for (i in backgroundList) {
                moviesList.add(
                    Movies(
                        i.id,
                        i.name,
                        i.rating,
                        i.imageUrl
                    )
                )
            }

            allMoviesAdapter = AllMoviesAdapter(moviesList, activity as Context)
            val mLayoutManager = LinearLayoutManager(activity)
            recyclerMovies.layoutManager = mLayoutManager
            recyclerMovies.itemAnimator = DefaultItemAnimator()
            recyclerMovies.adapter = allMoviesAdapter
            recyclerMovies.setHasFixedSize(true)
        }
    }

    class FavoritesAsync(context: Context): CoroutineAsyncTask<Void, Void, List<MovieEntity>>() {
        val db = Room.databaseBuilder(context, MovieDatabase::class.java, "movie-db").allowMainThreadQueries().build()

        override fun doInBackground(vararg params: Void?): List<MovieEntity> {
            return db.movieDao().getAllMovies()
        }
    }
}