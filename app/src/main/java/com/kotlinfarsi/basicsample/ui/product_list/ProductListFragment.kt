package com.kotlinfarsi.basicsample.ui.product_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kotlinfarsi.basicsample.R
import com.kotlinfarsi.basicsample.databinding.ListFragmentBinding
import com.kotlinfarsi.basicsample.db.entity.ProductEntity
import com.kotlinfarsi.basicsample.model.Product
import com.kotlinfarsi.basicsample.ui.MainActivity

class ProductListFragment : Fragment() {

    val TAG = "ProductListFragment"

    private lateinit var mBinding: ListFragmentBinding

    private lateinit var viewModel: ProductListViewModel

    private var mProductAdapter: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)

        mProductAdapter = ProductAdapter(object : ProductClickCallback {
            override fun onClick(product: Product) {
                if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    (requireActivity() as MainActivity).show(product)
                }
            }
        })

        mBinding.productsList.adapter = mProductAdapter
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)
        mBinding.lifecycleOwner = this

        subscribeUi(viewModel.getProducts())
    }

    private fun subscribeUi(liveData: LiveData<List<ProductEntity>>) {
        liveData.observe(viewLifecycleOwner, Observer { myProducts ->
            if (myProducts != null) {
                mBinding.isLoading = false
                mProductAdapter?.setProductList(myProducts)
            } else {
                mBinding.isLoading = true
            }
            mBinding.executePendingBindings()
        })
    }
}
