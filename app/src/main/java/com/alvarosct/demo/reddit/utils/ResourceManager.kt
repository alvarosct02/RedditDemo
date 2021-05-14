package com.alvarosct.demo.reddit.utils

import android.content.Context
import android.content.res.Resources
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes


class ResourceManager(context: Context) {
    private val resources: Resources = context.resources

    fun getString(@StringRes stringResId: Int): String {
        return resources.getString(stringResId)
    }

    fun getString(@StringRes stringResId: Int, vararg formatArgs: Any): String {
        return resources.getString(stringResId, *formatArgs)
    }

}
