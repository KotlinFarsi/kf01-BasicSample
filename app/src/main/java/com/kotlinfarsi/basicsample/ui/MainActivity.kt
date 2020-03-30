package com.kotlinfarsi.basicsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlinfarsi.basicsample.R
import com.kotlinfarsi.basicsample.model.Product
import com.kotlinfarsi.basicsample.ui.product.ProductFragment
import com.kotlinfarsi.basicsample.ui.product_list.ProductListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = ProductListFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, fragment.TAG)
                .commit()
        }
    }

    fun show(product: Product) {
        val productFragment = ProductFragment.forProduct(product.id!!)

        supportFragmentManager
            .beginTransaction()
            .addToBackStack("product")
            .replace(
                R.id.fragment_container,
                productFragment, null
            ).commit()
    }
}

