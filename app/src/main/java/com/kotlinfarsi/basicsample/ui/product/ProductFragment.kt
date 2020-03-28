package com.kotlinfarsi.basicsample.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kotlinfarsi.basicsample.R
import com.kotlinfarsi.basicsample.databinding.ProductFragmentBinding

import com.kotlinfarsi.basicsample.model.Comment

class ProductFragment : Fragment() {

    companion object {
        private val KEY_PRODUCT_ID = "product_id"

        fun forProduct(productId: Int): ProductFragment {
            val fragment = ProductFragment()
            val args = Bundle()
            args.putInt(KEY_PRODUCT_ID, productId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: ProductViewModel
    private lateinit var binding: ProductFragmentBinding
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_fragment, container, false)
        commentAdapter = CommentAdapter(object : CommentClickCallback {
            override fun onClick(comment: Comment?) {
                //no-op
            }
        })
        binding.commentList.adapter = commentAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(
                requireActivity().application,
                requireArguments().getInt(KEY_PRODUCT_ID)
            )
        ).get(ProductViewModel::class.java)

        binding.lifecycleOwner = this
        binding.productViewModel = viewModel

    }

}
