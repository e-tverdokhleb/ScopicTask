package com.scopictask.ys.presentation.fragments.login.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.scopictask.ys.R
import com.scopictask.ys.databinding.FragmentSignUpBinding
import com.scopictask.ys.presentation.fragments.BaseFragment
import com.scopictask.ys.presentation.utils.livedata.ActionNavigation
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
        viewModel.actionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ActionNavigation.ToSignInFragment ->
                    navigateToSignIn()

                ActionNavigation.ToWelcomeFragment ->
                    navigateToWelcome()

                else -> {} // no option
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showErrorMessage(it)
        }
    }

    private fun setClickListeners() {
        binding.btnSignUp.setOnClickListener {
            showLoading()
            performCreateUser()
        }
        binding.tvSignIn.setOnClickListener {
            viewModel.action(ActionNavigation.ToSignInFragment)
        }
    }

    private fun performCreateUser() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()
        val validationErrorMessage = getString(R.string.fields_shouldnt_be_empty)

        viewModel.createUserWithEmail(
            email, pass, validationErrorMessage
        )
    }

    private fun navigateToSignIn() {
        navigateTo(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
    }

    private fun navigateToWelcome() {
        navigateTo(SignUpFragmentDirections.actionSignUpFragmentToWelcomeFragment())
    }
}