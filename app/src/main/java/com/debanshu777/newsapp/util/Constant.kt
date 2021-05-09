package com.debanshu777.newsapp.util

class Constant {
    companion object {
        init {
            System.loadLibrary("api-keys")
        }

        private external fun getKeys(): String
        var API_KEY = getKeys()
        const val BASE_URL = "https://newsapi.org"
        const val SEARCH_NEWS_TIME_DELAY = 500L
        const val QUERY_PAGE_SIZE = 20
    }
}
