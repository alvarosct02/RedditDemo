package com.alvarosct.demo.reddit.di

import com.alvarosct.demo.reddit.BuildConfig
import com.alvarosct.demo.reddit.data.repository.PostRepository
import com.alvarosct.demo.reddit.data.repository.PostRepositoryImpl
import com.alvarosct.demo.reddit.data.source.api.PostApiSource
import com.alvarosct.demo.reddit.data.source.api.PostApiSourceImpl
import com.alvarosct.demo.reddit.data.source.api.RetrofitManager
import com.alvarosct.demo.reddit.data.source.local.AppDatabase
import com.alvarosct.demo.reddit.data.source.local.PostLocalSource
import com.alvarosct.demo.reddit.data.source.local.PostLocalSourceImpl
import com.alvarosct.demo.reddit.features.postDetail.PostDetailViewModel
import com.alvarosct.demo.reddit.features.postList.PostListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val retrofitModule = module {
    single { RetrofitManager(BuildConfig.REDDIT_BASE_URL) }
    single { get<RetrofitManager>().getOkHttpClient() }
    single { get<RetrofitManager>().getRetrofitClient(get()) }
    single { get<RetrofitManager>().getRedditApiService(get()) }
}

val roomModule = module {
    single { AppDatabase.buildAppDatabase(androidContext(), BuildConfig.DATABASE_NAME) }
    single { get<AppDatabase>().postDao() }
}

val appModule = module {
    single<PostApiSource> { PostApiSourceImpl(get()) }
    single<PostLocalSource> { PostLocalSourceImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get(), get()) }

}

val viewModelsModule = module {
    viewModel { PostListViewModel(get()) }
    viewModel { PostDetailViewModel(get()) }
}