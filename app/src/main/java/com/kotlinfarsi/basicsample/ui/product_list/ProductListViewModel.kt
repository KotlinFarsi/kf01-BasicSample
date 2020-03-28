package com.kotlinfarsi.basicsample.ui.product_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kotlinfarsi.basicsample.DataRepository

class ProductListViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mRepository: DataRepository

    fun getProducts(){
    }

    fun searchProducts(query: String){
    }
}
