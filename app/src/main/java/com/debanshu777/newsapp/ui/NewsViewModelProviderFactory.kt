package com.debanshu777.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.debanshu777.newsapp.repository.NewsRepository

class NewsViewModelProviderFactory(private val newsRepository: NewsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}