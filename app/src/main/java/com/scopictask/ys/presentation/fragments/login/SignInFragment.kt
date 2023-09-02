package com.scopictask.ys.presentation.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.scopictask.ys.R
import com.scopictask.ys.databinding.FragmentSignInBinding
import com.scopictask.ys.presentation.extentions.l
import com.scopictask.ys.presentation.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // if I correct understand what is HOC
        viewModel.checkIsUserLogged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setObservers()
        setClickListeners()
    }

    private fun setObservers() {
        viewModel.isUserSignedIn.observe(viewLifecycleOwner) {
            navigateToProfile()
        }
        viewModel.loginSuccessLiveData.observe(viewLifecycleOwner) {
            navigateToWelcome()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showErrorMessage(it)
        }
    }

    private fun setClickListeners() {
        binding.btnSignIn.setOnClickListener {
            showLoading(true)
            performSignIn()
        }
        binding.btnSignUp.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun performSignIn() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            viewModel.signInWithEmail(email, pass)
        } else {
            showSnackBar(getString(R.string.fields_shouldnt_be_empty))
        }
    }

    private fun navigateToProfile() {
        navigateTo(SignInFragmentDirections.actionSignInFragmentToProfileFragment())
    }

    private fun navigateToSignUp() {
        navigateTo(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
    }

    private fun navigateToWelcome() {
        navigateTo(SignInFragmentDirections.actionSignInFragmentToWelcomeFragment())
    }
}