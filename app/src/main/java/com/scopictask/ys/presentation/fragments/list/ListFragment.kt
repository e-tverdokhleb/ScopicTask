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
import com.scopictask.ys.presentation.adapter.ItemsAdapter
import com.scopictask.ys.presentation.adapter.SwipeToDeleteCallback
import com.scopictask.ys.presentation.dialog.DialogAddItem
import com.scopictask.ys.presentation.fragments.BaseFragment
import com.scopictask.ys.presentation.utils.Const.DIALOG_MESSAGE_RESULT
import com.scopictask.ys.presentation.utils.livedata.ActionNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>() {

    private val viewModel: ListViewModel by viewModels()
    private lateinit var listAdapter: ItemsAdapter

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

        listAdapter = ItemsAdapter()
        binding.rvItems.adapter = listAdapter
    }

    private fun setClickListeners() {
        binding.tvProfile.setOnClickListener {
            viewModel.action(ActionNavigation.ToProfileFragment)
        }
        binding.btnBack.setOnClickListener {
            viewModel.action(ActionNavigation.ToWelcomeFragment)
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
                listAdapter.updateList(list)
            }
        })
        viewModel.listLiveData.observe(viewLifecycleOwner) {
            listAdapter.updateList(it)
        }
        viewModel.actionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ActionNavigation.ToProfileFragment ->
                    navigateToProfile()

                is ActionNavigation.ToWelcomeFragment ->
                    navigateToWelcome()

                else -> {} // no option
            }
        }
    }

    private fun switchStorage(isFirebase: Boolean) {
        viewModel.switchToFirebaseStorage(isFirebase)
        updateDatabaseLabel(isFirebase)
    }

    private fun addItem(message: String) {
        viewModel.addItem(message)
    }

    private fun removeItem(position: Int) {
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