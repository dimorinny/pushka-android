package ru.nbsp.pushka.data.model.source

import io.realm.RealmObject

/**
 * Created by Dimorinny on 05.03.16.
 */
open class DataControl(
        open var type: String = "",
        open var title: String = "",
        open var options: String = ""
) : RealmObject()