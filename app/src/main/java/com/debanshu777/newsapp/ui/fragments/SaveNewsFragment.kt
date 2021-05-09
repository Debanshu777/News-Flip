package com.debanshu777.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.debanshu777.newsapp.R
import com.debanshu777.newsapp.adapters.NewsAdapter
import com.debanshu777.newsapp.ui.NewsActivity
import com.debanshu777.newsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SaveNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_saveNewsFragment_to_articleFragment, bundle)
        }
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Successfully Deleted Article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }

        viewModel.getSavedNews().observe(
            viewLifecycleOwner,
            Observer { articles ->
                if (articles.isNotEmpty()) {
                    Log.e("TAG", articles.toString())
                    rvSavedNews.visibility = View.VISIBLE
                    newsAdapter.differ.submitList(articles)
                    noItemLayout.visibility = View.GONE
                } else {
                    noItemLayout.visibility = View.VISIBLE
                }
            }
        )
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
