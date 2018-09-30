package com.oswaldo.simplelist.Models

data class Movie(
        var id: Int,
        var title: String,
        var posterPath: String,
        var overView: String,
        var releaseDate: String,
        var voteAverage: Double
)