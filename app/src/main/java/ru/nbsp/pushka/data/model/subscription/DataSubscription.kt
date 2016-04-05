package ru.nbsp.pushka.data.model.subscription

import io.realm.RealmObject

/**
 * Created by Dimorinny on 05.04.16.
 */
open class DataSubscription(
        open var id: String = "",
        open var title: String = "",
        open var icon: String = "",
        open var color: String = "",
        open var sourceTitle: String = ""
) : RealmObject()