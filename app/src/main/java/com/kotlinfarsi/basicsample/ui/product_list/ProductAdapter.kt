package com.kotlinfarsi.basicsample.ui.product_list

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kotlinfarsi.basicsample.R
import com.kotlinfarsi.basicsample.databinding.ProductItemBinding
import com.kotlinfarsi.basicsample.model.Product

class ProductAdapter(private val clickCallback: ProductClickCallback) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var mProductList: List<Product?>? = null

    init {
        setHasStableIds(true)
    }

    fun setProductList(productList: List<Product?>?) {
        if (mProductList == null) {
            mProductList = productList
            notifyItemRangeInserted(0, productList?.size!!)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize() = mProductList?.size!!

                override fun getNewListSize() = productList?.size!!


                override fun areItemsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ) = mProductList?.get(oldItemPosition)?.id ===
                        productList?.get(newItemPosition)?.id

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val newProduct = productList?.get(newItemPosition)
                    val oldProduct = mProductList?.get(oldItemPosition)
                    return (newProduct?.id === oldProduct?.id && TextUtils.equals(
                        newProduct?.description,
                        oldProduct?.description
                    )
                            && TextUtils.equals(newProduct?.name, oldProduct?.name)
                            && newProduct?.price === oldProduct?.price)
                }
            })
            mProductList = productList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding: ProductItemBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context), R.layout.product_item,
                parent, false
            )
        binding.callback = clickCallback
        return ProductViewHolder(binding)
    }

    override fun getItemCount() = if (mProductList == null) 0 else mProductList!!.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.product = mProductList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemId(position: Int): Long = mProductList?.get(position)?.id!!.toLong()

    inner class ProductViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}