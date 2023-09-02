package com.scopictask.ys.data.repository.realm.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Item() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var item: String = ""

    constructor(itemId: String = "") : this() {
        item = itemId
    }
}