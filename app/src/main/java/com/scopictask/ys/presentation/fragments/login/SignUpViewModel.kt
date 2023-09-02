package com.scopictask.ys.presentation.fragments.login

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.scopictask.ys.presentation.fragments.BaseViewModel
import com.scopictask.ys.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : BaseViewModel() {

    private val _userCreatedSuccessLiveData = SingleLiveEvent<Unit>()
    val userCreatedSuccessLiveData: LiveData<Unit>
        get() = _userCreatedSuccessLiveData

    fun createUserWithEmail(email: String, pass: String) {
        firebaseAuth.createUserWithEmailAndPassword(
            email, pass
        ).addOnSuccessListener {
            _userCreatedSuccessLiveData.call()
        }.addOnFailureListener {
            _error.value = it.message
        }
    }
}