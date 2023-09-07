package com.scopictask.ys.presentation.fragments.login.welcome

import androidx.lifecycle.LiveData
import com.scopictask.ys.presentation.fragments.BaseViewModel
import com.scopictask.ys.presentation.utils.livedata.ActionNavigation
import com.scopictask.ys.presentation.utils.livedata.SingleLiveEvent

class WelcomeViewModel : BaseViewModel() {

    private val _actionLiveData = SingleLiveEvent<ActionNavigation>()
    val actionLiveData: LiveData<ActionNavigation>
        get() = _actionLiveData

    fun action(action: ActionNavigation) {
        _actionLiveData.value = action
    }
}
