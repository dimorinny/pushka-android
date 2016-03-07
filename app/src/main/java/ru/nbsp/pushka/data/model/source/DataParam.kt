package ru.nbsp.pushka.data.model.source

import io.realm.RealmObject

/**
 * Created by Dimorinny on 26.02.16.
 */
open class DataParam(
        open var name: String = "",
        open var required: Boolean = false,
        open var control: DataControl = DataControl()
) : RealmObject()