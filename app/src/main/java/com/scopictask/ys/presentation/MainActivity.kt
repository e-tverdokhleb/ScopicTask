package com.scopictask.ys.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.scopictask.ys.R
import com.scopictask.ys.databinding.ActivityMainBinding
import com.scopictask.ys.presentation.extentions.l
import com.scopictask.ys.presentation.utils.LoadingView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LoadingView {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController =
            (supportFragmentManager.findFragmentById(R.id.hostContainer)
                    as NavHostFragment).navController
        navController.setGraph(R.navigation.nav_graph)
    }

    override fun showLoading(isLoading: Boolean) {
        binding.layoutLoading.layoutLoadingRoot.isVisible = isLoading
    }
}