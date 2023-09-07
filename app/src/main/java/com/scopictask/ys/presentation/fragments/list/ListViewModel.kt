package com.scopictask.ys.presentation.fragments.list

import androidx.lifecycle.LiveData
import com.scopictask.ys.data.repository.RepositoryImpl
import com.scopictask.ys.domain.repository.Observer
import com.scopictask.ys.presentation.fragments.BaseViewModel
import com.scopictask.ys.presentation.utils.livedata.ActionNavigation
import com.scopictask.ys.presentation.utils.livedata.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: RepositoryImpl,
) : BaseViewModel() {

    private val _listLiveData = SingleLiveEvent<MutableList<String>>()
    val listLiveData: LiveData<MutableList<String>>
        get() = _listLiveData

    fun switchToFirebaseStorage(value: Boolean) {
        repository.setFirebaseStorageActive(value)
        _listLiveData.value = repository.getItems()
    }

    private val _actionLiveData = SingleLiveEvent<ActionNavigation>()
    val actionLiveData: LiveData<ActionNavigation>
        get() = _actionLiveData

    fun action(action: ActionNavigation) {
        _actionLiveData.value = action
    }

    fun setRepositoryObserver(observer: Observer) {
        repository.setObserver(observer)
    }

    fun addItem(message: String) {
        repository.addItem(message)
    }

    fun removeItem(position: Int) {
        repository.removeItem(position)
    }

    fun loadItems() {
        _listLiveData.value = repository.getItems()
    }
}