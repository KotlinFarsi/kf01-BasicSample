package com.kotlinfarsi.basicsample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kotlinfarsi.basicsample.db.AppDatabase
import com.kotlinfarsi.basicsample.db.entity.ProductEntity

class DataRepository(private val database: AppDatabase) {

    private var mObservableProducts: MediatorLiveData<List<ProductEntity>> = MediatorLiveData()

    init {
        mObservableProducts.addSource(
            database.productDao().loadAllProducts()
        ) { productEntities ->
            if (database.getDatabaseCreated().value != null) {
                mObservableProducts.postValue(productEntities)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null
        private var LOCK = DataRepository::class

        operator fun invoke(database: AppDatabase): DataRepository {
            return instance ?: synchronized(LOCK) {
                instance ?: DataRepository(database)
            }
        }
    }

    fun getProducts(): LiveData<List<ProductEntity>> {
        return mObservableProducts
    }

    fun loadProduct(productId: Int): LiveData<ProductEntity> {
        return database.productDao().loadProduct(productId)
    }

    //TODO: Part 5 - (7) adding comments in repository
}