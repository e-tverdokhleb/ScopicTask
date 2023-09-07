package com.scopictask.ys.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.scopictask.ys.presentation.utils.livedata.SingleLiveEvent

open class BaseViewModel : ViewModel() {

    @Suppress("PropertyName")
    protected val _error = SingleLiveEvent<String>()
    val error: LiveData<String>
        get() = _error
}