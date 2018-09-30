package com.oswaldo.simplelist

import com.oswaldo.simplelist.Models.Movie
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id: Int = intent.getIntExtra("id", 0)
        getMovieDetail(id)
    }

    private fun getMovieDetail(id: Int){
        val url = String.format("https://api.themoviedb.org/3/movie/%s?api_key=%s&language=en-US", id, getString(R.string.movie_key))
        val imgUrl = "https://image.tmdb.org/t/p/w500"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->
                    val movie = Movie(id, response.getString("title"), imgUrl + response.getString("poster_path"),imgUrl + response.getString("backdrop_path"), response.getString("overview"), response.getString("release_date"), response.getDouble("vote_average"))
                    overview.text = movie.overView
                    Glide.with(this).load(movie.backdropPath).into(poster)
                },
                Response.ErrorListener {
                    Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
                })

        Volley.newRequestQueue(this).add(request)
    }
}
