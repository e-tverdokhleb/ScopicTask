package com.scopictask.ys.presentation.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.scopictask.ys.R
import com.scopictask.ys.databinding.FragmentListBinding
import com.scopictask.ys.domain.repository.Observer
import com.scopictask.ys.presentation.adapter.ListAdapter
import com.scopictask.ys.presentation.adapter.SwipeToDeleteCallback
import com.scopictask.ys.presentation.dialog.DialogAddItem
import com.scopictask.ys.presentation.extentions.l
import com.scopictask.ys.presentation.fragments.BaseFragment
import com.scopictask.ys.presentation.utils.Const.DIALOG_MESSAGE_RESULT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>() {

    private val viewModel: ListViewModel by viewModels()
    private lateinit var listAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    override fun onResume() {
        super.onResume()
        showLoading()
        loadList()
    }

    private fun setup() {
        initRecycler()
        setClickListeners()
        setObservers()
    }

    private fun loadList() {
        viewModel.loadItems()
    }

    private fun initRecycler() {
        binding.rvItems.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
        )

        ItemTouchHelper(SwipeToDeleteCallback(
            AppCompatResources.getDrawable(requireContext(), R.drawable.ico_delete)
        ) { adapterPosition ->
            removeItem(adapterPosition)
        }).attachToRecyclerView(binding.rvItems)

        listAdapter = ListAdapter()
        binding.rvItems.adapter = listAdapter
    }

    private fun setClickListeners() {
        binding.tvProfile.setOnClickListener {
            navigateToProfile()
        }
        binding.btnBack.setOnClickListener {
            navigateToWelcome()
        }
        binding.btnFloat.setOnClickListener {
            showAddItemDialog()
        }
        binding.cbFirebase.setOnCheckedChangeListener { _, isFirebase ->
            switchStorage(isFirebase)
        }
    }

    private fun setObservers() {
        setFragmentResultListener(DIALOG_MESSAGE_RESULT) { _, bundle ->
            bundle.getString(DIALOG_MESSAGE_RESULT)?.let {
                addItem(it)
            }
        }
        viewModel.setRepositoryObserver(object : Observer {
            override fun observer(list: MutableList<String>) {
                hideLoading()
                listAdapter.updateList(list)
            }
        })
        viewModel.listLiveData.observe(viewLifecycleOwner) {
            hideLoading()
            listAdapter.updateList(it)
        }
    }

    private fun switchStorage(isFirebase: Boolean) {
        showLoading()
        viewModel.switchToFirebaseStorage(isFirebase)
        updateDatabaseLabel(isFirebase)
    }

    private fun addItem(message: String) {
        showLoading()
        viewModel.addItem(message)
    }

    private fun removeItem(position: Int) {
        showLoading()
        viewModel.removeItem(position)
    }

    private fun updateDatabaseLabel(checked: Boolean) {
        val resString = if (checked) R.string.firebase else R.string.realm
        binding.cbFirebase.text = getString(resString)
    }

    private fun showAddItemDialog() {
        DialogAddItem.show(parentFragmentManager)
    }

    private fun navigateToWelcome() {
        navigateTo(ListFragmentDirections.actionListFragmentToWelcomeFragment())
    }

    private fun navigateToProfile() {
        navigateTo(ListFragmentDirections.actionListFragmentToProfileFragment())
    }
}