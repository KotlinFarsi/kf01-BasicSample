package com.kotlinfarsi.basicsample.ui.product

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlinfarsi.basicsample.BasicApp

class ProductViewModelFactory(
    private val application: Application,
    private val productId: Int
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductViewModel(
            application,
            (application as BasicApp).getRepository(),
            productId
        ) as T
    }
}