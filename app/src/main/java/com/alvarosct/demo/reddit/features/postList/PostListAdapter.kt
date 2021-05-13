package com.alvarosct.demo.reddit.features.postList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alvarosct.demo.reddit.databinding.ItemListPostBinding
import com.alvarosct.demo.reddit.models.PostModel


typealias OnPostClicked = (PostModel) -> Unit

class CurrencyListAdapter(
    private val onItemClick: OnPostClicked
) : ListAdapter<PostModel, CurrencyListAdapter.ViewHolder>(PostDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(onItemClick, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemListPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(onItemClick: OnPostClicked, post: PostModel) {
            binding.root.setOnClickListener { onItemClick.invoke(post) }
            binding.post = post
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListPostBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class PostDiffCallback : DiffUtil.ItemCallback<PostModel>() {
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem.id == newItem.id
    }
}