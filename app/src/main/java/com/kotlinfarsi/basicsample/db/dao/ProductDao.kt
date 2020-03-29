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

    @Query(
        "SELECT products.* FROM products JOIN productsFts ON (products.id = productsFts.rowid) "
                + "WHERE productsFts MATCH :query"
    )
    fun searchAllProducts(query: String): LiveData<List<ProductEntity>>

}
