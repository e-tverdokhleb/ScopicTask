package com.scopictask.ys.presentation.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.scopictask.ys.databinding.FragmentProfileBinding
import com.scopictask.ys.presentation.fragments.BaseFragment
import com.scopictask.ys.presentation.utils.livedata.ActionNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()

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
        setObservers()
    }

    private fun setUI() {
        binding.tvEmail.text = FirebaseAuth.getInstance().currentUser?.email ?: ""
    }

    private fun setObservers() {
        viewModel.actionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ActionNavigation.ToListFragment ->
                    navigateToList()

                is ActionNavigation.LogOut ->
                    navigateToSignIn()

                else -> {} // no option
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnBack.setOnClickListener {
            viewModel.action(ActionNavigation.ToListFragment)
        }
        binding.btnLogOut.setOnClickListener {
            viewModel.action(ActionNavigation.LogOut)
        }
    }

    private fun navigateToSignIn() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToSignInFragment())
    }

    private fun navigateToList() {
        navigateTo(ProfileFragmentDirections.actionProfileFragmentToListFragment())
    }
}