package com.scopictask.ys.presentation.fragments.login

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.scopictask.ys.presentation.fragments.BaseViewModel
import com.scopictask.ys.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : BaseViewModel() {

    private val _loginSuccessLiveData = SingleLiveEvent<Unit>()
    val loginSuccessLiveData: LiveData<Unit>
        get() = _loginSuccessLiveData

    private val _isUserSignedIn = SingleLiveEvent<Unit>()
    val isUserSignedIn: LiveData<Unit>
        get() = _isUserSignedIn

    fun checkIsUserLogged() {
        firebaseAuth.currentUser?.let {
            _isUserSignedIn.call()
        }
    }

    fun signInWithEmail(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(
            email, pass
        ).addOnSuccessListener {
            _loginSuccessLiveData.call()
        }.addOnFailureListener {
            _error.value = it.message
        }
    }
}