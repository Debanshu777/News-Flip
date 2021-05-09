package com.debanshu777.newsapp.models

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)
