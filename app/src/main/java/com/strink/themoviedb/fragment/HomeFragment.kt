package com.strink.themoviedb.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.strink.themoviedb.BuildConfig
import com.strink.themoviedb.R
import com.strink.themoviedb.adapter.AllMoviesAdapter
import com.strink.themoviedb.model.Movies
import com.strink.themoviedb.util.ConnectionManager
import org.json.JSONException

class HomeFragment : Fragment() {

    private lateinit var recyclerMovies: RecyclerView
    private lateinit var allMoviesAdapter: AllMoviesAdapter
    private var moviesList = arrayListOf<Movies>()
    private lateinit var progressBar: ProgressBar
    private lateinit var rlLoading: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = view?.findViewById(R.id.progressBar) as ProgressBar
        rlLoading = view.findViewById(R.id.rlLoading) as RelativeLayout
        rlLoading.visibility = View.VISIBLE

        setupRecycler(view)

        return view
    }

    private fun setupRecycler(view: View) {
        recyclerMovies = view.findViewById(R.id.recyclerMovies)
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "https://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.TMDB_API_KEY
        Log.d("URL::::::::", "setupRecycler: $url")
        if (ConnectionManager().isNetworkAvailable(activity as Context)) {
            val jsonObjectRequest = object : JsonObjectRequest(
                Method.GET,
                url,
                null,
                Response.Listener { response ->
                    rlLoading.visibility = View.GONE
                    try {
                        val resArray = response.getJSONArray("results")

                        for (i in 0 until resArray.length()) {
                            val resObject = resArray.getJSONObject(i)
                            val movie = Movies(
                                resObject.getString("id").toInt(),
                                resObject.getString("title"),
                                resObject.getString("vote_average"),
                                resObject.getString("poster_path")
                            )
                            Log.d("Title ", resObject.getString("title"))
                            moviesList.add(movie)
                            if (activity != null) {
                                allMoviesAdapter = AllMoviesAdapter(moviesList, activity as Context)
                                recyclerMovies.layoutManager = LinearLayoutManager(activity as Context)
                                recyclerMovies.adapter = allMoviesAdapter
                                recyclerMovies.setHasFixedSize(true)
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(activity as Context, it?.message, Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        } else {
            val builder =AlertDialog.Builder(activity as Context)
            builder.setTitle("Error")
            builder.setMessage("No Internet Connection found. Please connect to the internet and re-open the app.")
            builder.setCancelable(false)
            builder.setPositiveButton("Ok") { _, _ ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            builder.create()
            builder.show()
        }
    }
}