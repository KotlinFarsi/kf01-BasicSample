package com.kotlinfarsi.basicsample.db

import com.kotlinfarsi.basicsample.db.entity.ProductEntity
import java.util.*
import kotlin.collections.ArrayList

object DataGenerator {
    private val FIRST = arrayOf(
        "Special edition", "New", "Cheap", "Quality", "Used"
    )
    private val SECOND = arrayOf(
        "Three-headed Monkey", "Rubber Chicken", "Pint of Grog", "Monocle"
    )
    private val DESCRIPTION = arrayOf(
        "is finally here", "is recommended by Stan S. Stanman",
        "is the best sold product on Mêlée Island", "is \uD83D\uDCAF", "is ❤️", "is fine"
    )
    private val COMMENTS = arrayOf(
        "Comment 1", "Comment 2", "Comment 3", "Comment 4", "Comment 5", "Comment 6"
    )


    fun generateProducts(): List<ProductEntity> {
        val products: MutableList<ProductEntity> =
            ArrayList(FIRST.size * SECOND.size)
        val rnd = Random()
        for (i in FIRST.indices) {
            for (j in SECOND.indices) {
                val product = ProductEntity()
                product.name = FIRST[i] + " " + SECOND[j]
                product.description = product.name.toString() + " " + DESCRIPTION[j]
                product.price = rnd.nextInt(240)
                product.id = FIRST.size * i + j + 1
                products.add(product)
            }
        }
        return products
    }


    fun generateCommentsForProducts() {
        //TODO: Part 5 - (4) implement this method for generating comments for products
    }
}