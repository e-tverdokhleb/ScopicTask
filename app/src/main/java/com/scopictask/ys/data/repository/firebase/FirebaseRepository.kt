package com.scopictask.ys.data.repository.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.scopictask.ys.domain.repository.Observer
import com.scopictask.ys.domain.repository.Repository
import com.scopictask.ys.presentation.extentions.l

const val SCOPIC_DATABASE = "SCOPIC_DATABASE"
const val SCOPIC_ITEMS = "ITEMS"

class FirebaseRepository : Repository {

    private var items = mutableListOf<String>()
    private lateinit var observer: Observer

    init {
        subscribeDataChange()
    }

    override fun setObserver(observer: Observer) {
        this.observer = observer
    }

    override fun getItems(): MutableList<String> {
        l(items)
        return items
    }

    private fun setItems(items: MutableList<String>) {
        Firebase.database
            .getReference(SCOPIC_DATABASE)
            .child(SCOPIC_ITEMS)
            .setValue(items)
    }

    override fun addItem(message: String) {
        val items = getItems()
        items.add(message)
        setItems(items)
    }

    override fun removeItem(position: Int) {
        val items = getItems()
        items.removeAt(position)
        setItems(items)
    }

    private fun subscribeDataChange() {
        Firebase.database
            .getReference(SCOPIC_DATABASE)
            .child(SCOPIC_ITEMS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    items = try {
                        snapshot.value?.let {
                            it as MutableList<String>
                        } ?: mutableListOf()
                    } catch (e: Exception) {
                        mutableListOf()
                    }

                    observer.observer(items)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}
