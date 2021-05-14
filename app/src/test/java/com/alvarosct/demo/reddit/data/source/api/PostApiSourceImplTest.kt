package com.alvarosct.demo.reddit.data.source.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alvarosct.demo.reddit.data.source.api.models.RedditPost
import com.alvarosct.demo.reddit.data.source.api.responses.DataWrap
import com.alvarosct.demo.reddit.data.source.api.responses.Listing
import com.alvarosct.demo.reddit.data.source.api.responses.TopRedditResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

class PostApiSourceImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var redditApiService: RedditApiService

    @InjectMockKs
    lateinit var postApiSource: PostApiSourceImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    private fun wrapInTopRedditResponse(vararg redditPosts: RedditPost): TopRedditResponse {
        return DataWrap(
            data = Listing(
                children = redditPosts.map { DataWrap(data = it) },
                after = redditPosts.last().id
            )
        )
    }


    @Test
    fun `when getTopPosts the apiSource is being called`() = runBlockingTest {

        val redditPost = RedditPost(
            id = "ZZZ",
            title = "Hello World!",
            subredditNamePrefixed = "r/foo",
            thumbnail= "http://wwww.fake.com/image.png",
            url= "http://wwww.fake.com/image.png",
        )
        val body = wrapInTopRedditResponse(redditPost)
        coEvery { redditApiService.getTopPosts(any(), any()) } returns Response.success(body)

        postApiSource.getTopPosts("A", 20)

        coVerify { redditApiService.getTopPosts("A", 20) }

    }

    @Test
    fun `when getTopPosts the model conversion is done properly`() = runBlockingTest {

        val redditPost = RedditPost(
            id = "ZZZ",
            title = "Hello World!",
            subredditNamePrefixed = "r/foo",
            thumbnail= "http://wwww.fake.com/image.png",
            url= "http://wwww.fake.com/image.png",
        )
        val body = wrapInTopRedditResponse(redditPost)
        coEvery { redditApiService.getTopPosts(any(), any()) } returns Response.success(body)

        val (_, postList) = postApiSource.getTopPosts("A", 20)

        coVerify { redditApiService.getTopPosts("A", 20) }

        assertEquals(1, postList.size)
        val post = postList.first()
        assertEquals("ZZZ", post.id)
        assertEquals("r/foo", post.subredditName)
        assert(post.isImage)

    }

    @Test
    fun `receiving multiple post at once when getTopPosts`() = runBlockingTest {

        val redditPost = RedditPost(
            id = "ZZZ",
            title = "Hello World!",
            subredditNamePrefixed = "r/foo",
            thumbnail= "http://wwww.fake.com/image.png",
            url= "http://wwww.fake.com/image.png",
        )
        val body = wrapInTopRedditResponse(redditPost, redditPost, redditPost)
        coEvery { redditApiService.getTopPosts(any(), any()) } returns Response.success(body)

        val (_, postList) = postApiSource.getTopPosts("A", 20)

        coVerify { redditApiService.getTopPosts("A", 20) }
        assertEquals(3, postList.size)

    }

    @Test
    fun `when getTopPosts return empty if no response`() = runBlockingTest {

        val errorBody = "{}".toResponseBody("application/json".toMediaTypeOrNull())
        coEvery { redditApiService.getTopPosts(any(), any()) } returns Response.error(401, errorBody)

        val (_, postList) = postApiSource.getTopPosts("A", 20)

        coVerify { redditApiService.getTopPosts("A", 20) }
        assertEquals(0, postList.size)

    }

}