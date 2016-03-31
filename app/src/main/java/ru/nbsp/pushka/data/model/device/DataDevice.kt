package ru.nbsp.pushka.data.model.device

import io.realm.RealmObject

/**
 * Created by Dimorinny on 31.03.16.
 */
open class DataDevice(
        open var id: String = "",
        open var name: String = "",
        open var type: String = "",
        open var token: String = "",
        open var enabled: Boolean = false,
        open var editable: Boolean = false
) : RealmObject()