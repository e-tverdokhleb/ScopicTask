package com.scopictask.ys.presentation.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.scopictask.ys.R
import com.scopictask.ys.databinding.FragmentSignUpBinding
import com.scopictask.ys.presentation.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater)
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
        viewModel.userCreatedSuccessLiveData.observe(viewLifecycleOwner) {
            navigateToWelcome()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showErrorMessage(it)
        }
    }

    private fun setClickListeners() {
        binding.btnSignUp.setOnClickListener {
            showLoading(true)
            performCreateUser()
        }
        binding.tvSignIn.setOnClickListener {
            navigateToSignIn()
        }
    }

    private fun performCreateUser() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            viewModel.createUserWithEmail(email, pass)
        } else {
            showSnackBar(getString(R.string.fields_shouldnt_be_empty))
        }
    }

    private fun navigateToSignIn() {
        navigateTo(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
    }

    private fun navigateToWelcome() {
        navigateTo(SignUpFragmentDirections.actionSignUpFragmentToWelcomeFragment())
    }
}