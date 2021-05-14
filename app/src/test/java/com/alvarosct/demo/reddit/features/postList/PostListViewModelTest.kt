package com.alvarosct.demo.reddit.features.postList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alvarosct.demo.reddit.TestCoroutineRule
import com.alvarosct.demo.reddit.data.repository.PostRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PostListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    lateinit var repository: PostRepository

    lateinit var viewModel: PostListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        coEvery { repository.observePosts() } returns MutableLiveData()

    }

    @Test
    fun `verify fetchPosts is being called at init`() = testCoroutineRule.runBlockingTest {

        coEvery { repository.fetchPosts(any(), any(), any()) } returns "BBB"

        viewModel = PostListViewModel(repository)

        coVerify(exactly = 1) { repository.fetchPosts(any(), any(), any()) }

    }

    @Test
    fun `verify fetchPosts is being called for refresh`() = testCoroutineRule.runBlockingTest {

        coEvery { repository.fetchPosts(any(), any(), any()) } returns "BBB"

        viewModel = PostListViewModel(repository)
        viewModel.refreshPosts()

        coVerify(exactly = 2) { repository.fetchPosts(any(), any(), any()) }
        coVerify(exactly = 1) { repository.fetchPosts(any(), any(), isRefresh = true) }

    }

    @Test
    fun `verify fetchPosts is being called for requestMore`() = testCoroutineRule.runBlockingTest {

        coEvery { repository.fetchPosts(any(), any(), any()) } returns "BBB"

        viewModel = PostListViewModel(repository)
        viewModel.requestMorePosts()

        coVerify(exactly = 2) { repository.fetchPosts(any(), any(), isRefresh = false) }

    }

    @Test
    fun `verify lastAfter is being passed for requestMore`() = testCoroutineRule.runBlockingTest {

        coEvery { repository.fetchPosts(any(), any(), any()) } returns "BBB"

        viewModel = PostListViewModel(repository)
        viewModel.requestMorePosts()

        coVerify(exactly = 1) { repository.fetchPosts(after = null, any(), any()) }
        coVerify(exactly = 1) { repository.fetchPosts(after = "BBB", any(), any()) }

    }

    @Test
    fun `verify lastAfter is being reset for refreshing`() = testCoroutineRule.runBlockingTest {

        coEvery { repository.fetchPosts(any(), any(), any()) } returns "BBB"

        viewModel = PostListViewModel(repository)
        viewModel.requestMorePosts()

        coVerify(exactly = 1) { repository.fetchPosts(after = null, any(), any()) }
        coVerify(exactly = 1) { repository.fetchPosts(after = "BBB", any(), any()) }

        viewModel.refreshPosts()
        coVerify(exactly = 1) { repository.fetchPosts(after = null, any(), isRefresh = true) }

    }

    @Test
    fun `verify the limit param is being used`() = testCoroutineRule.runBlockingTest {

        coEvery { repository.fetchPosts(any(), any(), any()) } returns "BBB"

        viewModel = PostListViewModel(repository)
        viewModel.requestMorePosts()
        viewModel.refreshPosts()

        coVerify(exactly = 3) {
            repository.fetchPosts(
                any(),
                limit = PostListViewModel.POST_PAGE_SIZE,
                any()
            )
        }

    }
}