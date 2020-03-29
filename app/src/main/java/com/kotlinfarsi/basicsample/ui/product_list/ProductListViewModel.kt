package com.kotlinfarsi.basicsample.ui.product_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kotlinfarsi.basicsample.DataRepository

class ProductListViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mRepository: DataRepository
    //TODO: Part 4 - (1) creating private observable value for get product to use

    fun getProducts(){
        //TODO: Part 4 - (2) modifying get product to use observable value and pass it as a live data
    }

    fun searchProducts(query: String){
    }
}
