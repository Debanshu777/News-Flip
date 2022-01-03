package com.debanshu777.newsFlix.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.debanshu777.newsFlix.R
import com.debanshu777.newsFlix.dataSource.db.ArticleDatabase
import com.debanshu777.newsFlix.dataSource.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_news)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]
        bottomNavigationView.setupWithNavController(newsNaveHostFragment.findNavController())
    }

    fun hideBottomNav() {
        bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNav() {
        bottomNavigationView.visibility = View.VISIBLE
    }
}
