package com.kotlinfarsi.basicsample.ui.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kotlinfarsi.basicsample.DataRepository

class ProductViewModel(
    application: Application,
    private val repository: DataRepository,
    private val productId: Int
) :
    AndroidViewModel(application) {
    //TODO: Part 6 - (1) create a observable field for product view to include

    fun getComments(){
        //TODO: Part 6 - (2) create a method for loading comments
    }

    fun getObservableProduct() {
        //TODO: Part 6 - (3) create a method for load a product
    }
}
