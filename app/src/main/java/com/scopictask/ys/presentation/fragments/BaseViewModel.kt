package com.scopictask.ys.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.scopictask.ys.presentation.utils.SingleLiveEvent

open class BaseViewModel : ViewModel() {

    protected val _error = SingleLiveEvent<String>()
    val error: LiveData<String>
        get() = _error
}