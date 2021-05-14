package com.alvarosct.demo.reddit.features.postDetail

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alvarosct.demo.reddit.TestCoroutineRule
import com.alvarosct.demo.reddit.models.PostModel
import com.alvarosct.demo.reddit.utils.FileUtils
import com.alvarosct.demo.reddit.utils.ResourceManager
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PostDetailViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    lateinit var resourceManager: ResourceManager

    @MockK
    lateinit var fileUtils: FileUtils

    @InjectMockKs
    lateinit var viewModel: PostDetailViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { resourceManager.getString(any()) } returns ""
    }

    @Test
    fun `verify postLiveData is null before calling setPost`() = testCoroutineRule.runBlockingTest {

        val postModel = PostModel(
            id = "ZZZ",
            title = "Hello World!",
            subredditName = "r/foo",
            thumbnailUrl = "http://wwww.fake.com/image.png",
            sourceUrl = "http://wwww.fake.com/image.png",
        )

        assertNull(viewModel.postLiveData.value)

        viewModel.setPost(postModel)

        assertNotNull(viewModel.postLiveData.value)
        assertEquals("ZZZ", viewModel.postLiveData.value!!.id)
    }

    @Test
    fun `verify postLiveData is filled properly after calling setPost`() =
        testCoroutineRule.runBlockingTest {

            val postModel = PostModel(
                id = "ZZZ",
                title = "Hello World!",
                subredditName = "r/foo",
                thumbnailUrl = "http://wwww.fake.com/image.png",
                sourceUrl = "http://wwww.fake.com/image.png",
            )

            assertNull(viewModel.postLiveData.value)

            viewModel.setPost(postModel)

            assertNotNull(viewModel.postLiveData.value)
            assertEquals("ZZZ", viewModel.postLiveData.value!!.id)
        }

    @Test
    fun `verify fileUtils is used when saving a bitmap`() = testCoroutineRule.runBlockingTest {

        every { resourceManager.getString(any(), any(), any()) } returns ""
        coEvery { fileUtils.saveBitmapToFile(any(), any()) } returns true

        val bitmap = mockk<Bitmap>()
        viewModel.saveBitmap(bitmap)

        verify { fileUtils.saveBitmapToFile(bitmap, any()) }

    }
}