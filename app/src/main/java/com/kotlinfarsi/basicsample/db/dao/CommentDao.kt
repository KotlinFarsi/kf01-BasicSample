package com.kotlinfarsi.basicsample.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlinfarsi.basicsample.db.entity.CommentEntity

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments where productId = :productId")
    fun loadComments(productId: Int): LiveData<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comments: List<CommentEntity>)
}

