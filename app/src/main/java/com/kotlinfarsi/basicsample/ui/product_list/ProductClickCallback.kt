package com.kotlinfarsi.basicsample.ui.product_list

import com.kotlinfarsi.basicsample.model.Product

interface ProductClickCallback {
    fun onClick(product: Product)
}