package com.scopictask.ys.domain.repository

interface Repository {
    fun addItem(message: String)
    fun removeItem(position: Int)
    fun getItems(): MutableList<String>
    fun setObserver(observer: Observer)
}