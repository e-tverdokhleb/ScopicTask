package com.scopictask.ys.presentation.fragments.login.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.scopictask.ys.R
import com.scopictask.ys.databinding.FragmentSignInBinding
import com.scopictask.ys.presentation.fragments.BaseFragment
import com.scopictask.ys.presentation.utils.livedata.ActionNavigation
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
        viewModel.actionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ActionNavigation.ToProfileFragment ->
                    navigateToProfile()

                is ActionNavigation.ToSignUpFragment ->
                    navigateToSignUp()

                is ActionNavigation.ToWelcomeFragment ->
                    navigateToWelcome()

                else -> {} // no option
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showErrorMessage(it)
        }
    }

    private fun setClickListeners() {
        binding.btnSignIn.setOnClickListener {
            showLoading()
            performSignIn()
        }
        binding.btnSignUp.setOnClickListener {
            viewModel.action(ActionNavigation.ToSignUpFragment)
        }
    }

    private fun performSignIn() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()
        val validationErrorMessage = getString(R.string.fields_shouldnt_be_empty)

        viewModel.signInWithEmail(
            email, pass, validationErrorMessage
        )
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