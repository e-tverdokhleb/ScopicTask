package com.scopictask.ys.data.repository

import com.scopictask.ys.domain.repository.Observer
import com.scopictask.ys.domain.repository.Repository
import javax.inject.Inject
import javax.inject.Named

class RepositoryImpl @Inject constructor(
    @Named("firebase")
    private val firebaseDataSource: Repository,
    @Named("realm")
    private val realmDataSource: Repository,
) : Repository {

    private var isFirebaseActive = true

    fun setFirebaseStorageActive(value: Boolean) {
        isFirebaseActive = value
    }

    override fun setObserver(observer: Observer) {
        realmDataSource.setObserver(observer)
        firebaseDataSource.setObserver(observer)
    }

    override fun addItem(message: String) {
        if (isFirebaseActive) firebaseDataSource.addItem(message)
        else realmDataSource.addItem(message)
    }

    override fun removeItem(position: Int) {
        if (isFirebaseActive) firebaseDataSource.removeItem(position)
        else realmDataSource.removeItem(position)
    }

    override fun getItems(): MutableList<String> {
        return if (isFirebaseActive) firebaseDataSource.getItems()
        else realmDataSource.getItems()
    }
}