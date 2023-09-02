package com.scopictask.ys.presentation.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scopictask.ys.databinding.FragmentWelcomeBinding
import com.scopictask.ys.presentation.fragments.BaseFragment

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnList.setOnClickListener {
            navigateTo(WelcomeFragmentDirections.actionWelcomeFragmentToListFragment())
        }
    }
}