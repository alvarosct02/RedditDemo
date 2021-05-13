package com.alvarosct.demo.reddit.features.postList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvarosct.demo.reddit.databinding.FragmentPostListBinding
import com.alvarosct.demo.reddit.features.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class PostListFragment : BaseFragment() {

    private lateinit var binding: FragmentPostListBinding
    private val viewModel by viewModel<PostListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeRefresh()
        setupRecyclerView()


    }

    private fun setupSwipeRefresh() {
        binding.srlPosts.setOnRefreshListener {
            viewModel.refreshPosts()
        }
        viewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
            isRefreshing.takeIf { it == false }?.let { binding.srlPosts.isRefreshing = it }
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvPosts.apply {
            adapter = CurrencyListAdapter(viewModel)
            layoutManager = linearLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (viewModel.isLoading.value == true || viewModel.isLoadingMore.value == true || viewModel.isRefreshing.value == true) return
                    val visibleItemCount: Int = linearLayoutManager.childCount
                    val totalItemCount: Int = linearLayoutManager.itemCount
                    val pastVisibleItems: Int = linearLayoutManager.findFirstVisibleItemPosition()
                    if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                        viewModel.requestMorePosts()
                    }
                }
            })
        }
    }

}