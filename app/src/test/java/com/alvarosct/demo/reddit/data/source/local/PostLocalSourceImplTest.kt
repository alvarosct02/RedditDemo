package com.alvarosct.demo.reddit.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alvarosct.demo.reddit.data.source.local.dao.PostDao
import com.alvarosct.demo.reddit.models.PostModel
import io.mockk.MockKAnnotations
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PostLocalSourceImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var postDao: PostDao

    @InjectMockKs
    lateinit var postLocalSource: PostLocalSourceImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coJustRun { postDao.insertAll(any()) }
        coJustRun { postDao.insert(any()) }
    }

    @Test
    fun `verify dao is being called when saving Posts`() = runBlockingTest {

        val postModel = PostModel(
            id = "ZZZ",
            title = "Hello World!",
            subredditName = "r/foo",
            thumbnailUrl= "http://wwww.fake.com/image.png",
            sourceUrl= "http://wwww.fake.com/image.png",
        )

        postLocalSource.savePosts(listOf(postModel))

        coVerify(exactly = 0) { postDao.insert(any()) }
        coVerify { postDao.insertAll(any()) }

    }

    @Test
    fun `verify inserting is being done at once when saving Posts`() = runBlockingTest {

        val postModel = PostModel(
            id = "ZZZ",
            title = "Hello World!",
            subredditName = "r/foo",
            thumbnailUrl= "http://wwww.fake.com/image.png",
            sourceUrl= "http://wwww.fake.com/image.png",
        )

        postLocalSource.savePosts(listOf(postModel, postModel, postModel))

        coVerify(exactly = 0) { postDao.insert(any()) }
        coVerify(exactly = 1) { postDao.insertAll(any()) }

    }

}