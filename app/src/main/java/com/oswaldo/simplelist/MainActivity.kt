package com.oswaldo.simplelist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.oswaldo.simplelist.Adapters.MoviesAdapter
import com.oswaldo.simplelist.Helpers.OnItemClickListener
import com.oswaldo.simplelist.Helpers.addOnItemClickListener
import com.oswaldo.simplelist.Models.Movie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tool_bar.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var movieList: ArrayList<Movie>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Simple List"
        setSupportActionBar(toolbar)

        movies_list.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val item: Movie = movieList!![position]
                openDetail(item.id)
            }
        })

        getMovies()
    }

    fun openDetail(id: Int){
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra("id", id)
        startActivity(i)
    }

    private fun getMovies(){
        val url = String.format("https://api.themoviedb.org/3/movie/top_rated?api_key=%s&language=en-US&page=1", getString(R.string.movie_key))
        val imgUrl = "https://image.tmdb.org/t/p/w500"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->
                    val movies = response.getJSONArray("results")
                    var temp: JSONObject
                    for (i in 0 until movies.length()){
                        temp = movies.getJSONObject(i)
                        movieList?.add(Movie(temp.getInt("id"), temp.getString("title"),imgUrl + temp.getString("poster_path"),imgUrl + temp.getString("backdrop_path"), temp.getString("overview"), temp.getString("release_date"), temp.getDouble("vote_average")))
                    }
                    Log.d("getMovies()", movies.toString())

                    movies_list.layoutManager = LinearLayoutManager(this)
                    movies_list.hasFixedSize()
                    movies_list.adapter = MoviesAdapter(movieList, this)
                },
                Response.ErrorListener {
                    Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
                })

        Volley.newRequestQueue(this).add(request)
    }
}
