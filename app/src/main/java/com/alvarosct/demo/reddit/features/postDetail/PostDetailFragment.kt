package com.alvarosct.demo.reddit.features.postDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.alvarosct.demo.reddit.databinding.FragmentPostDetailBinding
import com.alvarosct.demo.reddit.features.base.BaseFragment
import com.alvarosct.demo.reddit.models.PostModel
import org.koin.android.viewmodel.ext.android.viewModel

class PostDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentPostDetailBinding
    private val viewModel by viewModel<PostDetailViewModel>()
    private val args: PostDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setPost(args.post)
    }

}