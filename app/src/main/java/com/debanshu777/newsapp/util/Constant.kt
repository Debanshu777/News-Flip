package com.debanshu777.newsapp.util

class Constant {
    companion object {
        init {
            System.loadLibrary("api-keys")
        }

        private external fun getKeys(): String

        // using gradle_secrets_plugin to add it to local.properties as NEWS_API=my_key
        // and accessing it via Buildconfig.NEWS_API
        var API_KEY = getKeys() // or Buildconfig.NEWS_API
        const val BASE_URL = "https://newsapi.org"
        const val SEARCH_NEWS_TIME_DELAY = 500L
        const val QUERY_PAGE_SIZE = 20
    }
}
