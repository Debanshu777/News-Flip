package com.debanshu777.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.debanshu777.newsapp.R
import com.debanshu777.newsapp.adapters.NewsAdapter
import com.debanshu777.newsapp.ui.NewsActivity
import com.debanshu777.newsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*

class SaveNewsFragment:Fragment(R.layout.fragment_saved_news){

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_saveNewsFragment_to_articleFragment,bundle)
        }
    }
    private  fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        rvSavedNews.apply {
            adapter=newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }

}