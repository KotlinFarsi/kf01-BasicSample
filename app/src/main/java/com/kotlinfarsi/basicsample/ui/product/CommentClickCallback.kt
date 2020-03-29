package com.kotlinfarsi.basicsample.ui.product

import com.kotlinfarsi.basicsample.model.Comment

interface CommentClickCallback {
    fun onClick(comment: Comment?)
}