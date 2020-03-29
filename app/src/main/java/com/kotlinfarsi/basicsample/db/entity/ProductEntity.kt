package com.kotlinfarsi.basicsample.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotlinfarsi.basicsample.model.Product


@Entity(tableName = "products")
data class ProductEntity @JvmOverloads constructor(
    @PrimaryKey
    override var id: Int? = null,
    override var name: String? = null,
    override var description: String? = null,
    override var price: Int? = null
) : Product
