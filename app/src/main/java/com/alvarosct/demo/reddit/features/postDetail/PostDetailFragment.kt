package com.alvarosct.demo.reddit.features.postDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.navArgs
import com.alvarosct.demo.reddit.R
import com.alvarosct.demo.reddit.databinding.FragmentPostDetailBinding
import com.alvarosct.demo.reddit.features.base.BaseFragment
import com.alvarosct.demo.reddit.utils.EventObserver
import com.google.android.material.snackbar.Snackbar
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

        viewModel.saveToastEvent.observe(viewLifecycleOwner, EventObserver {
            showSavedImageSnackBar()
        })

        binding.btSave.setOnClickListener {
            val bitmap = binding.ivContent.drawable.toBitmap()
            viewModel.saveBitmap(bitmap)
        }
    }

    private fun showSavedImageSnackBar() {
        Snackbar.make(binding.root, R.string.save_complete, Snackbar.LENGTH_SHORT).apply {
            setAction(R.string.label_open) {
                this.dismiss()
            }
        }.show()
    }

}