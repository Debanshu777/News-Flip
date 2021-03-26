package com.debanshu777.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.debanshu777.newsapp.R
import com.debanshu777.newsapp.ui.NewsActivity
import com.debanshu777.newsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment:Fragment(R.layout.fragment_article){

    lateinit var viewModel: NewsViewModel
    private val args:ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
        val article=args.article
        webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)
        }
        fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view,"News Saved",Snackbar.LENGTH_LONG).show()
        }
    }
}