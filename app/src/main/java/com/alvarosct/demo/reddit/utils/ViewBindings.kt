package com.alvarosct.demo.reddit.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


@BindingAdapter("app:items")
fun <T> RecyclerView.setItems(items: List<T>?) {
    items?.let {
        (this.adapter as? ListAdapter<T, *>)?.submitList(items)
    }
}

@BindingAdapter("isVisible")
fun View.isViewVisible(value: Boolean?) {
    this.isVisible = value ?: false
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(this).load(it).into(this)
    }
}

