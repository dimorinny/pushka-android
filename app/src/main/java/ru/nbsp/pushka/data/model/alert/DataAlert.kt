package ru.nbsp.pushka.data.model.alert

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by Dimorinny on 25.02.16.
 */
open class DataAlert(
        open var id: String = "",
        open var title: String = "",
        open var text: String = "",
        open var photo: String? = null,
        open var date: Long = 0,
        open var sourceImage: String = "",
        open var sourceTitle: String = "",
        open var shareLink: String = "",
        open var color: String = "",
        open var actions: RealmList<DataAction> = RealmList()
) : RealmObject()