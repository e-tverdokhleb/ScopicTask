package com.scopictask.ys.presentation.fragments.profile

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.scopictask.ys.presentation.extentions.l
import com.scopictask.ys.presentation.fragments.BaseViewModel
import com.scopictask.ys.presentation.utils.livedata.ActionNavigation
import com.scopictask.ys.presentation.utils.livedata.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : BaseViewModel() {

    private val _actionLiveData = SingleLiveEvent<ActionNavigation>()
    val actionLiveData: LiveData<ActionNavigation>
        get() = _actionLiveData

    fun action(action: ActionNavigation) {
        when (action) {
            is ActionNavigation.LogOut -> performLogOut()

            else -> _actionLiveData.value = action
        }
    }

    private fun performLogOut() {
        firebaseAuth.signOut()
        firebaseAuth.currentUser ?: run {
            _actionLiveData.value = ActionNavigation.LogOut
        }
    }
}
