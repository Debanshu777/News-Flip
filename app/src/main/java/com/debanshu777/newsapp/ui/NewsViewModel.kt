package com.debanshu777.newsapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_MOBILE
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.debanshu777.newsapp.NewsApplication
import com.debanshu777.newsapp.models.Article
import com.debanshu777.newsapp.models.NewsResponse
import com.debanshu777.newsapp.repository.NewsRepository
import com.debanshu777.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    app: Application,
    private val newsRepository: NewsRepository
) : AndroidViewModel(app) {
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    val optionNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var optionNewsPage = 1
    var optionNewsResponse: NewsResponse? = null

    init {
        getBreakingNews("in")
        optionNews("economics")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        safeBreakingNewsCalls(countryCode)
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        safeSearchNewsCalls(searchQuery)
    }
    fun optionNews(searchQuery: String) = viewModelScope.launch {
        safeOptionNewsCalls(searchQuery)
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleOptionNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                optionNewsPage++
                if (optionNewsResponse == null) {
                    optionNewsResponse = resultResponse
                } else {
                    val oldArticles = optionNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    private suspend fun safeSearchNewsCalls(searchQuery: String) {
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchNews.postValue(Resource.Error("Network Error"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeOptionNewsCalls(searchQuery: String) {
        optionNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                optionNews.postValue(handleOptionNewsResponse(response))
            } else {
                optionNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> optionNews.postValue(Resource.Error("Network Error"))
                else -> optionNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeBreakingNewsCalls(countryCode: String) {
        breakingNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> breakingNews.postValue(Resource.Error("Network Error"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}
