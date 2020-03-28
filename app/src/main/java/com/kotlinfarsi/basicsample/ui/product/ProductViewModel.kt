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

    fun getComments(){
    }

    fun getObservableProduct() {
    }
}
