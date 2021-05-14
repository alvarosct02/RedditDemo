package com.alvarosct.demo.reddit.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alvarosct.demo.reddit.data.source.api.PostApiSource
import com.alvarosct.demo.reddit.data.source.local.PostLocalSource
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class PostRepositoryImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var apiSource: PostApiSource

    @MockK
    lateinit var localSource: PostLocalSource

    @InjectMockKs
    lateinit var postRepository: PostRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when fetchPosts the apiSource is being called`() = runBlockingTest {

        coEvery { apiSource.getTopPosts(any(), any()) } returns ("B" to listOf())
        coJustRun { localSource.savePosts(any()) }

        postRepository.fetchPosts("A", 20, false)

        coVerify { apiSource.getTopPosts("A", 20) }

    }

    @Test
    fun `when fetching posts the observable is emitting the values`() = runBlockingTest {

        coEvery { apiSource.getTopPosts(any(), any()) } returns ("B" to listOf(mockk(), mockk()))
        coJustRun { localSource.savePosts(any()) }

        postRepository.fetchPosts("A", 20, false)

        coVerify { apiSource.getTopPosts("A", 20) }
        assertEquals(2, postRepository.observePosts().value?.size ?: 0)

    }

    @Test
    fun `when fetching posts the observable is accumulating values`() = runBlockingTest {

        coEvery { apiSource.getTopPosts(any(), any()) } returns ("B" to listOf(mockk(), mockk()))
        coJustRun { localSource.savePosts(any()) }

        postRepository.fetchPosts("A", 20, false)
        postRepository.fetchPosts("A", 20, false)
        postRepository.fetchPosts("A", 20, false)

        coVerify(exactly = 3) { apiSource.getTopPosts(any(), 20) }
        assertEquals(6, postRepository.observePosts().value?.size ?: 0)

    }

    @Test
    fun `when fetching posts by refreshing the post list is cleared first`() = runBlockingTest {

        coEvery { apiSource.getTopPosts(any(), any()) } returns ("B" to listOf(mockk(), mockk()))
        coJustRun { localSource.savePosts(any()) }

        postRepository.fetchPosts("A", 20, false)
        postRepository.fetchPosts("A", 20, false)
        postRepository.fetchPosts("A", 20, true)

        coVerify(exactly = 3) { apiSource.getTopPosts(any(), 20) }
        assertEquals(2, postRepository.observePosts().value?.size ?: 0)

    }

    @Test
    fun `when fetching posts the values are being stored in the localSource`() = runBlockingTest {

        coEvery { apiSource.getTopPosts(any(), any()) } returns ("B" to listOf(mockk(), mockk()))
        coJustRun { localSource.savePosts(any()) }

        postRepository.fetchPosts("A", 20, false)
        postRepository.fetchPosts("A", 20, false)

        coVerify(exactly = 2) { localSource.savePosts(any()) }

    }

    @Test
    fun `when fetching posts if the service fails `() = runBlockingTest {

        coEvery { apiSource.getTopPosts(any(), any()) } returns ("B" to listOf(mockk(), mockk()))
        coJustRun { localSource.savePosts(any()) }
        coEvery { localSource.getPosts() } returns listOf(mockk())

        postRepository.fetchPosts("A", 20, false)
        postRepository.fetchPosts("A", 20, false)
        postRepository.fetchPosts("A", 20, false)

        assertEquals(6, postRepository.observePosts().value?.size ?: 0)


        coEvery { apiSource.getTopPosts(any(), any()) } throws Exception()
        postRepository.fetchPosts("A", 20, false)

        assertEquals(1, postRepository.observePosts().value?.size ?: 0)

    }

}