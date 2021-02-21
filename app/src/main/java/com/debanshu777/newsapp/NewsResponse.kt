package com.debanshu777.newsapp

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)