package com.scopictask.ys.presentation.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.scopictask.ys.R
import com.scopictask.ys.presentation.extentions.showSnackBar
import com.scopictask.ys.presentation.utils.LoadingView

open class BaseFragment<T : ViewBinding> : Fragment() {

    protected var _binding: T? = null
    protected val binding get() = _binding!!
    private var loadingListener: LoadingView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoadingView) {
            loadingListener = context
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    protected fun navigateTo(directions: NavDirections) {
        loadingListener?.showLoading(false)
        findNavController().navigate(directions)
    }

    protected fun showMessage(
        message: String?,
        title: String? = null,
        action: (() -> Unit)? = null,
    ) {
        loadingListener?.showLoading(false)
        MaterialAlertDialogBuilder(requireContext()).setTitle(title).setMessage(message)
            .setPositiveButton(getString(android.R.string.ok)) { dialog, _ ->
                action?.invoke()
                dialog.dismiss()
            }.show()
    }

    protected fun showErrorMessage(
        message: String?,
        title: String = getString(R.string.unexpected_error),
        action: (() -> Unit)? = null,
    ) {
        loadingListener?.showLoading(false)
        MaterialAlertDialogBuilder(requireContext()).setTitle(title).setMessage(message)
            .setPositiveButton(getString(android.R.string.ok)) { dialog, _ ->
                action?.invoke()
                dialog.dismiss()
            }.show()
    }

    protected fun showLoading(show: Boolean = true) {
        loadingListener?.showLoading(show)
    }

    protected fun hideLoading() {
        loadingListener?.showLoading(false)
    }

    protected fun showSnackBar(message: String?) {
        message?.let {
            loadingListener?.showLoading(false)
            requireView().showSnackBar(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingListener?.showLoading(false)
        _binding = null
        loadingListener = null
    }
}