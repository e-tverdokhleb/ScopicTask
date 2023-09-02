package com.scopictask.ys.data.repository

import com.scopictask.ys.data.repository.firebase.FirebaseRepository
import com.scopictask.ys.data.repository.realm.RealmRepository
import com.scopictask.ys.domain.repository.Observer
import com.scopictask.ys.domain.repository.Repository
import com.scopictask.ys.presentation.extentions.l
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val realmRepository: RealmRepository,
) : Repository {

    private var isFirebaseActive = true

    fun setFirebaseStorageActive(value: Boolean) {
        isFirebaseActive = value
    }

    override fun setObserver(observer: Observer) {
        realmRepository.setObserver(observer)
        firebaseRepository.setObserver(observer)
    }

    override fun addItem(message: String) {
        if (isFirebaseActive) firebaseRepository.addItem(message)
        else realmRepository.addItem(message)
    }

    override fun removeItem(position: Int) {
        if (isFirebaseActive) firebaseRepository.removeItem(position)
        else realmRepository.removeItem(position)
    }

    override fun getItems(): MutableList<String> {
        return if (isFirebaseActive) firebaseRepository.getItems()
        else realmRepository.getItems()
    }
}