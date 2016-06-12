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
        open var sourceTitle: String = "",
        open var sourceId: String = "",
        open var values: String = "",
        open var sound: Boolean = false,
        open var notification: Boolean = false
) : RealmObject()