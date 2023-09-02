package com.scopictask.ys.presentation.dialog

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.graphics.Insets
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.scopictask.ys.R
import com.scopictask.ys.databinding.DialogActionConfirmBinding
import com.scopictask.ys.presentation.utils.Const.DIALOG_MESSAGE_RESULT

class DialogAddItem : BottomSheetDialogFragment() {

    private lateinit var binding: DialogActionConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = DialogActionConfirmBinding.inflate(inflater)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ViewCompat.setOnApplyWindowInsetsListener(requireDialog().window?.decorView!!) { _, insets ->
                val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                val navigationBarHeight =
                    insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
                binding.root.setPadding(0, 0, 0, imeHeight - navigationBarHeight)
                insets
            }
        } else {
            requireDialog().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnConfirmAction.setOnClickListener {
            val message = binding.etMessage.text.toString()
            setFragmentResult(
                DIALOG_MESSAGE_RESULT,
                bundleOf(DIALOG_MESSAGE_RESULT to message)
            )
            this.dismiss()
        }
        binding.btnCancel.setOnClickListener {
            this.dismiss()
        }
    }


    companion object {

        fun show(fragmentManager: FragmentManager) {
            DialogAddItem().apply {
                show(fragmentManager, tag)
            }
        }
    }
}