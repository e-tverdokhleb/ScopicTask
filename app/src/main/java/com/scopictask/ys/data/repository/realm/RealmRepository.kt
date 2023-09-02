package com.scopictask.ys.data.repository.realm

import com.scopictask.ys.data.repository.realm.model.Item
import com.scopictask.ys.domain.repository.Observer
import com.scopictask.ys.presentation.extentions.l
import com.scopictask.ys.domain.repository.Repository
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RealmRepository : Repository {

    private lateinit var observer: Observer

    override fun setObserver(observer: Observer) {
        this.observer = observer
    }

    private fun notifyDataChanged() {
        val items = getItems()
        observer.observer(items)
    }

    private fun openRealm(): Realm {
        val config = RealmConfiguration.create(schema = setOf(Item::class))
        return Realm.open(config)
    }

    private fun writeBlocking(block: (MutableRealm) -> Unit) {
        val realm = openRealm()
        realm.writeBlocking {
            block(this)
        }
        realm.close()
    }

    override fun addItem(message: String) {
        writeBlocking {
            it.copyToRealm(Item().apply {
                item = message
            })
        }
        notifyDataChanged()
    }

    override fun removeItem(position: Int) {
        writeBlocking {
            val writeTransactionItems = it.query(Item::class).find()
            it.delete(writeTransactionItems[position])
        }
        notifyDataChanged()
    }

    override fun getItems(): MutableList<String> {
        val realm = openRealm()
        val allItems = realm.query(Item::class).find()
        val items = allItems.map { it.item }.toMutableList()
        realm.close()

        return items
    }
}