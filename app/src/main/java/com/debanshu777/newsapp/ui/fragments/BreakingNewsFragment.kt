package com.debanshu777.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.debanshu777.newsapp.R
import com.debanshu777.newsapp.adapters.NewsAdapter
import com.debanshu777.newsapp.adapters.OptionsAdapter
import com.debanshu777.newsapp.models.Option
import com.debanshu777.newsapp.ui.NewsActivity
import com.debanshu777.newsapp.ui.NewsViewModel
import com.debanshu777.newsapp.util.Constant.Companion.QUERY_PAGE_SIZE
import com.debanshu777.newsapp.util.Resource
import com.debanshu777.newsapp.util.UserPreferences
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_onboarding3.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreakingNewsFragment:Fragment(R.layout.fragment_breaking_news){

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private lateinit var optionAdapter: OptionsAdapter
    lateinit var list:ArrayList<Option>
    private val TAG = "BreakingNewsFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            val value = UserPreferences.getValue(requireContext(), "testKey", default = "123")
            Log.e("TAG", "Here$value")
            if(value=="123"){
                (activity as NewsActivity).hideBottomNav()
                findNavController().navigate(R.id.action_breakingNewsFragment_to_viewPagerFragment)
            }else {
                (activity as NewsActivity).showBottomNav()
                nameSet.text = value
            }
        }
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults/ QUERY_PAGE_SIZE+2
                        isLastPage= viewModel.breakingNewsPage == totalPages
                        if(isLastPage){
                            rvBreakingNews.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity,"An error occured: $message",Toast.LENGTH_LONG).show()
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading=false
    }
    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading=true
    }

    var isLoading=false
    var isLastPage=false
    var isScrolling=false

    private val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling=true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager=recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition =layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount=layoutManager.childCount
            val totalItemCount=layoutManager.itemCount

            val isNotLoadingAndNotLastPage=!isLoading && !isLastPage
            val isAtLastItem= firstVisibleItemPosition+visibleItemCount>=totalItemCount
            val isNotAtBeginning= firstVisibleItemPosition >=0
            val isTotalMoreThanVisible=totalItemCount>=QUERY_PAGE_SIZE
            val shouldPaginate=isNotLoadingAndNotLastPage && isAtLastItem &&
                    isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                viewModel.getBreakingNews("in")
                isScrolling=false
            }
        }
    }
    private fun data():ArrayList<Option>{
        list=ArrayList()
        list.add(Option("url","Economics"))
        list.add(Option("url","Business"))
        list.add(Option("url","Country"))
        list.add(Option("url","Technology"))
        list.add(Option("url","Country"))
        Log.e("Tag",list.size.toString())
        return list
    }
    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        optionAdapter= OptionsAdapter(data())
        rvOptions.apply {
            adapter=optionAdapter
            layoutManager =  LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        }
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager =  LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}