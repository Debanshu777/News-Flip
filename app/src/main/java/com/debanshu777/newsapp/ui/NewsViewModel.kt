package com.debanshu777.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debanshu777.newsapp.models.NewsResponse
import com.debanshu777.newsapp.repository.NewsRepository
import com.debanshu777.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.SecureCacheResponse

class NewsViewModel(
    private val newsRepository: NewsRepository
):ViewModel() {
    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage=1

    init {
        getBreakingNews("in")
    }
    private fun getBreakingNews(countryCode:String)=viewModelScope.launch{
        breakingNews.postValue(Resource.Loading())
        val response=newsRepository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                resultResponse -> return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}