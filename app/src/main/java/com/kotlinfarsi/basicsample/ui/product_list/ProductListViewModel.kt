package com.kotlinfarsi.basicsample.ui.product_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kotlinfarsi.basicsample.BasicApp
import com.kotlinfarsi.basicsample.DataRepository
import com.kotlinfarsi.basicsample.db.entity.ProductEntity

class ProductListViewModel(application: Application) : AndroidViewModel(application) {

    private var mRepository: DataRepository

    private var mObservableProducts: MediatorLiveData<List<ProductEntity>>

    init {
        mObservableProducts = MediatorLiveData()
        // set by default null, until we get data from the database.
        mObservableProducts.value = null
        mRepository = (application as BasicApp).getRepository()
        val products = mRepository.getProducts()
        mObservableProducts.addSource(products , mObservableProducts::setValue)
    }

    fun getProducts(): LiveData<List<ProductEntity>> {
        return mObservableProducts
    }

    fun searchProducts(query: String){
        //TODO: Part 7 - (6) creating a method for searching products
    }
}
