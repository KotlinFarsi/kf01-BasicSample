package com.kotlinfarsi.basicsample.ui.product

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kotlinfarsi.basicsample.DataRepository
import com.kotlinfarsi.basicsample.db.entity.CommentEntity
import com.kotlinfarsi.basicsample.db.entity.ProductEntity

class ProductViewModel(
    application: Application,
    private val repository: DataRepository,
    private val productId: Int
) :
    AndroidViewModel(application) {
    var product: ObservableField<ProductEntity> = ObservableField()

    fun getComments(): LiveData<List<CommentEntity>> {
        return repository.loadComments(productId)
    }

    fun getObservableProduct(): LiveData<ProductEntity> {
        return repository.loadProduct(productId)
    }
}
