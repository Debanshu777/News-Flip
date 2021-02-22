package com.debanshu777.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.debanshu777.newsapp.R
import com.debanshu777.newsapp.ui.NewsActivity
import com.debanshu777.newsapp.ui.NewsViewModel

class ArticleFragment:Fragment(R.layout.fragment_article){

    lateinit var viewModel: NewsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
    }
}