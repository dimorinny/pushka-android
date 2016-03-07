package ru.nbsp.pushka.data.model.source

import io.realm.RealmObject

/**
 * Created by Dimorinny on 05.03.16.
 */
open class DataCategory(
        open var id: String = "",
        open var name: String = "",
        open var image: String = ""
) : RealmObject()