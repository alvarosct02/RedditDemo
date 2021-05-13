package com.alvarosct.demo.reddit.features.postList

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvarosct.demo.reddit.R
import com.alvarosct.demo.reddit.databinding.FragmentPostListBinding
import com.alvarosct.demo.reddit.features.base.BaseFragment
import com.alvarosct.demo.reddit.models.PostModel
import com.google.android.material.snackbar.Snackbar
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

    private fun getLayoutManager(ctx: Context) = when (resources.configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> LinearLayoutManager(ctx)
        else -> GridLayoutManager(ctx, 2)
    }

    private fun setupRecyclerView() {
        val customLayoutManager = getLayoutManager(requireContext())
        binding.rvPosts.apply {
            adapter = CurrencyListAdapter(::openPostDetail)
            layoutManager = customLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (viewModel.isLoading.value == true || viewModel.isLoadingMore.value == true || viewModel.isRefreshing.value == true) return
                    val visibleItemCount: Int = customLayoutManager.childCount
                    val totalItemCount: Int = customLayoutManager.itemCount
                    val pastVisibleItems: Int = customLayoutManager.findFirstVisibleItemPosition()
                    if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                        viewModel.requestMorePosts()
                    }
                }
            })
        }
    }

    private fun openPostDetail(post: PostModel) {

        if (post.isImage) {
            findNavController().navigate(
                PostListFragmentDirections.actionPostListFragmentToPostDetailFragment(post)
            )
        } else {
            Snackbar.make(binding.root, R.string.unsupported_format, Snackbar.LENGTH_SHORT).apply {
                setAction(R.string.label_ok) {
                    this.dismiss()
                }
            }.show()
        }
    }

}