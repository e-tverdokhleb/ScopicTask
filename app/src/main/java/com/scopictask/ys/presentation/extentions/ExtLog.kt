package com.scopictask.ys.presentation.extentions

import android.util.Log

const val TAG = "XXX"

fun Any?.lg(a: Any? = null, tag: String = TAG) {
    val claz = this?.toString()?.let {
        val lastPoint = it.lastIndexOf(".").takeIf { i -> (i > 0) && (i <= it.length) } ?: 0
        it.removeRange(0, lastPoint)
    }
    val msg = "${claz ?: ""}: ${a ?: "_"} "
    when (a) {
        is Exception -> Log.e(tag, msg)
        else -> Log.d(tag, msg)
    }
}

fun Any?.l(a: Any? = null) {
    lg(": $a ")
}