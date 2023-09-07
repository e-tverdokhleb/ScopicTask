package com.scopictask.ys.presentation.fragments.login.signup

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.scopictask.ys.presentation.fragments.BaseViewModel
import com.scopictask.ys.presentation.utils.livedata.ActionNavigation
import com.scopictask.ys.presentation.utils.livedata.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : BaseViewModel() {

    private val _actionLiveData = SingleLiveEvent<ActionNavigation>()
    val actionLiveData: LiveData<ActionNavigation>
        get() = _actionLiveData

    fun action(action: ActionNavigation) {
        _actionLiveData.value = action
    }

    fun createUserWithEmail(email: String, pass: String, errorMsg: String) {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(
                email, pass
            ).addOnSuccessListener {
                _actionLiveData.value =
                    ActionNavigation.ToWelcomeFragment
            }.addOnFailureListener {
                _error.value = it.message
            }
        } else {
            _error.value = errorMsg
        }
    }
}