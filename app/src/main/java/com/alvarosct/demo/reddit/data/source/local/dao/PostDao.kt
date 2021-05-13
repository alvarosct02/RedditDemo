package com.alvarosct.demo.reddit.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alvarosct.demo.reddit.data.source.local.entities.PostEntity


@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(postEntity: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(postEntityList: List<PostEntity>)

    @Query("SELECT * FROM PostEntity ORDER BY upVotes DESC")
    fun observePosts(): LiveData<List<PostEntity>>

    @Query("SELECT * FROM PostEntity ORDER BY upVotes DESC")
    suspend fun getPosts(): List<PostEntity>

}