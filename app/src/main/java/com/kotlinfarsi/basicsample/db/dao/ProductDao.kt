package com.kotlinfarsi.basicsample.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlinfarsi.basicsample.db.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun loadAllProducts(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM products where id = :productId")
    fun loadProduct(productId: Int): LiveData<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductEntity>)

    //TODO: Part 7 - (2) add search all products
}
