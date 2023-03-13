package com.debanshu777.newsFlip.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.debanshu777.newsFlip.R
import com.debanshu777.newsFlip.ui.adapters.BreakingNewsAdapter
import com.debanshu777.newsFlip.ui.adapters.NewsAdapter
import com.debanshu777.newsFlip.ui.adapters.OptionsAdapter
import com.debanshu777.newsFlip.dataSource.models.Option
import com.debanshu777.newsFlip.ui.NewsActivity
import com.debanshu777.newsFlip.ui.NewsViewModel
import com.debanshu777.newsFlip.util.Constant.Companion.QUERY_PAGE_SIZE
import com.debanshu777.newsFlip.util.Resource
import com.debanshu777.newsFlip.util.UserPreferences
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_onboarding3.*
import kotlinx.coroutines.launch

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private lateinit var optionAdapter: OptionsAdapter
    private lateinit var breakingNewsAdapter: BreakingNewsAdapter
    lateinit var list: ArrayList<Option>
    private val TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            val value = UserPreferences.getValue(requireContext(), "nameKey", default = "123")
            Log.e("TAG", "Here$value")
            if (value == "123") {
                (activity as NewsActivity).hideBottomNav()
                findNavController().navigate(R.id.action_breakingNewsFragment_to_viewPagerFragment)
            } else {
                (activity as NewsActivity).showBottomNav()
                val name = "Hey, $value!"
                nameSet.text = name
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
        breakingNewsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
        optionAdapter.setOnItemClickListener {
            viewModel.optionNews(it.title)
            Log.e(TAG, it.title)
        }

        viewModel.optionNews.observe(
            viewLifecycleOwner,
            Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles.toList())
                            val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                            isLastPage = viewModel.breakingNewsPage == totalPages
                            if (isLastPage) {
                                rvOptionNews.setPadding(0, 0, 0, 0)
                            }
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Toast.makeText(
                                activity,
                                "An error occured: $message",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            Log.e(TAG, "An error occured: $message")
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        )
        viewModel.breakingNews.observe(
            viewLifecycleOwner,
            Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            breakingNewsAdapter.differ.submitList(newsResponse.articles.toList())
                            val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                            isLastPage = viewModel.breakingNewsPage == totalPages
                            if (isLastPage) {
                                rvBreakingNews.setPadding(0, 0, 0, 0)
                            }
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Toast.makeText(
                                activity,
                                "An error occured: $message",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            Log.e(TAG, "An error occured: $message")
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        )
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem &&
                    isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getBreakingNews("in")
                isScrolling = false
            }
        }
    }

    private fun data(): ArrayList<Option> {
        list = ArrayList()
        list.add(
            Option(
                "https://img.freepik.com/free-photo/business-people-shaking-hands-together_53876-20488.jpg?size=626&ext=jpg",
                "Business"
            )
        )
        list.add(
            Option(
                "https://www.asmibanquet.com/wp-content/uploads/2017/10/Entertainment.jpg",
                "Entertainment"
            )
        )
        list.add(
            Option(
                "https://images.everydayhealth.com/homepage/health-topics-2.jpg?sfvrsn=757370ae_2",
                "Health"
            )
        )
        list.add(
            Option(
                "https://thumbs-prod.si-cdn.com/s-jZTk0XtVmp-89MlOgFXqaAVe4=/fit-in/1600x0/https://public-media.si-cdn.com/filer/29/0f/290fb8c0-1872-46e5-8c12-235742905def/science_smithsonian_magazine_booklist_2019.png",
                "Science"
            )
        )
        list.add(
            Option(
                "https://mongooseagency.com/files/3415/9620/1413/Return_of_Sports.jpg",
                "Sports"
            )
        )
        list.add(
            Option(
                "https://www.universal-rights.org/wp-content/uploads/2019/09/30212411048_2a1d7200e2_z-1.jpg",
                "Technology"
            )
        )
        Log.e("Tag",
            list.size.toString())
        return list
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        breakingNewsAdapter = BreakingNewsAdapter()
        optionAdapter = OptionsAdapter(data())
        rvBreakingNews.apply {
            adapter = breakingNewsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
        rvOptions.apply {
            adapter = optionAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
        rvOptionNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}