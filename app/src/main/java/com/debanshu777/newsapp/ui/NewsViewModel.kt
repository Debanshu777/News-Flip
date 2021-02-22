package com.debanshu777.newsapp.ui

import androidx.lifecycle.ViewModel
import com.debanshu777.newsapp.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
):ViewModel() {
}