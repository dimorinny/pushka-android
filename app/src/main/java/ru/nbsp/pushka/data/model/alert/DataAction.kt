package ru.nbsp.pushka.data.model.alert

import io.realm.RealmObject

/**
 * Created by Dimorinny on 15.03.16.
 */
open class DataAction(
        open var type: String = "",
        open var value: String = ""
) : RealmObject()