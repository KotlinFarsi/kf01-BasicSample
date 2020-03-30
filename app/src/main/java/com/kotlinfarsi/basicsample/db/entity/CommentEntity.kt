package com.kotlinfarsi.basicsample.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kotlinfarsi.basicsample.model.Comment
import java.util.*

@Entity(
    tableName = "comments",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["productId"]
        )
    ]
)
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Int? = null,
    override var productId: Int? = null,
    override var text: String? = null,
    override var postedAt: Date? = null
) : Comment

