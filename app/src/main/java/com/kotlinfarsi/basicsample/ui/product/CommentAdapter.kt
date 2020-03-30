package com.kotlinfarsi.basicsample.ui.product

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kotlinfarsi.basicsample.R
import com.kotlinfarsi.basicsample.databinding.CommentItemBinding
import com.kotlinfarsi.basicsample.model.Comment

class CommentAdapter(private val mCommentClickCallback: CommentClickCallback?) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    private var mCommentList: List<Comment>? = null

    fun setCommentList(comments: List<Comment>) {
        if (mCommentList == null) {
            mCommentList = comments
            notifyItemRangeInserted(0, comments.size)
        } else {
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mCommentList!!.size
                }

                override fun getNewListSize(): Int {
                    return comments.size
                }

                override fun areItemsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val old =
                        mCommentList!![oldItemPosition]
                    val comment =
                        comments[newItemPosition]
                    return old.id === comment.id
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val old =
                        mCommentList!![oldItemPosition]
                    val comment =
                        comments[newItemPosition]
                    return old.id === comment.id && old.postedAt === comment.postedAt && old.productId === comment.productId && TextUtils.equals(
                        old.text,
                        comment.text
                    )
                }
            })
            mCommentList = comments
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = DataBindingUtil
            .inflate<CommentItemBinding>(
                LayoutInflater.from(parent.context), R.layout.comment_item,
                parent, false
            )
        binding.callback = mCommentClickCallback
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.binding.comment = mCommentList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (mCommentList == null) 0 else mCommentList!!.size
    }

    inner class CommentViewHolder(val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}
