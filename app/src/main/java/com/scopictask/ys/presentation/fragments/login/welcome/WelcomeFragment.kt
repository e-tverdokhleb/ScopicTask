package com.scopictask.ys.presentation.fragments.login.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.scopictask.ys.databinding.FragmentWelcomeBinding
import com.scopictask.ys.presentation.fragments.BaseFragment
import com.scopictask.ys.presentation.utils.livedata.ActionNavigation
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    private val viewModel: WelcomeViewModel by viewModels()

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
        setObservers()
    }

    private fun setObservers() {
        viewModel.actionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ActionNavigation.ToListFragment ->
                    navigateTo(WelcomeFragmentDirections.actionWelcomeFragmentToListFragment())

                else -> {}  // no option
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnList.setOnClickListener {
            viewModel.action(ActionNavigation.ToListFragment)
        }
    }
}