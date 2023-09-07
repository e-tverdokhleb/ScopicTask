package com.scopictask.ys.presentation.fragments.login.signin

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.scopictask.ys.presentation.fragments.BaseViewModel
import com.scopictask.ys.presentation.utils.livedata.ActionNavigation
import com.scopictask.ys.presentation.utils.livedata.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : BaseViewModel() {

    private val _actionLiveData = SingleLiveEvent<ActionNavigation>()
    val actionLiveData: LiveData<ActionNavigation>
        get() = _actionLiveData

    fun action(action: ActionNavigation) {
        _actionLiveData.value = action
    }

    fun checkIsUserLogged() {
        firebaseAuth.currentUser?.let {
            _actionLiveData.value =
                ActionNavigation.ToProfileFragment
        }
    }

    fun signInWithEmail(email: String, pass: String, errorMsg: String) {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(
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