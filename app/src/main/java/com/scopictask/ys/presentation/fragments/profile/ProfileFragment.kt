package com.scopictask.ys.presentation.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.scopictask.ys.databinding.FragmentProfileBinding
import com.scopictask.ys.presentation.fragments.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
    }

    private fun setup() {
        setUI()
        setOnClickListeners()
    }

    private fun setUI() {
        binding.tvEmail.text = FirebaseAuth.getInstance().currentUser?.email ?: ""
    }

    private fun setOnClickListeners() {
        binding.btnBack.setOnClickListener {
            navigateToList()
        }

        binding.btnLogOut.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        FirebaseAuth.getInstance().currentUser ?: navigateToSignIn()
    }

    private fun navigateToSignIn() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToSignInFragment())
    }

    private fun navigateToList() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToListFragment())
    }
}