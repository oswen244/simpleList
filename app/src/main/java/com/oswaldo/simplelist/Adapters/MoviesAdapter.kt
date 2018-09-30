package com.oswaldo.simplelist.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.oswaldo.simplelist.Models.Movie
import com.oswaldo.simplelist.R
import kotlinx.android.synthetic.main.movie_item.view.*
import kotlin.collections.ArrayList

class MoviesAdapter(private val items: ArrayList<Movie>?, val context: Context): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items?.get(position))

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind (item: Movie?){
            //binding.item = item

            view.movie_title.text = item?.title
            view.movie_overview.text = item?.overView
            view.year.text = formatDate(item?.releaseDate)
            Glide.with(context).load(item?.posterPath).into(view.poster)

        }

        private fun formatDate(date: String?): String? {
            return date?.substring(0,4)
        }
    }

}
