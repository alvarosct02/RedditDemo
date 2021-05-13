package com.alvarosct.demo.reddit.data.source.api.models

import com.squareup.moshi.Json


data class RedditPost(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "subreddit_name_prefixed") val subredditNamePrefixed: String?,
    @field:Json(name = "thumbnail") val thumbnail: String?,
    @field:Json(name = "is_video") val isVideo: Boolean?,
    @field:Json(name = "ups") val upVotes: Long,
    @field:Json(name = "preview") val preview: Preview?,
    @field:Json(name = "url") val url: String?,
    @field:Json(name = "created_utc") val createdUtc: Long,
) {

}

data class Preview(
    @field:Json(name = "images") val images: List<PreviewImage>,
)

data class PreviewImage(
    @field:Json(name = "source") val source: ImageSource,
    @field:Json(name = "resolutions") val resolutions: List<ImageSource>,
)

data class ImageSource(
    @field:Json(name = "url") val url: String,
    @field:Json(name = "width") val width: Int,
    @field:Json(name = "height") val height: Int,
)