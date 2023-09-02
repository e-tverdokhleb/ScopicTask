package com.scopictask.ys.domain.repository

interface Observer {
    fun observer(list: MutableList<String>)
}