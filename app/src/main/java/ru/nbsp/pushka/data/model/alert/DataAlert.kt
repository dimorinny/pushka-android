package ru.nbsp.pushka.data.model.alert

import io.realm.RealmObject

/**
 * Created by Dimorinny on 25.02.16.
 */
open class DataAlert(
        open var id: String = "",
        open var title: String = "",
        open var text: String = "",
        open var photo: String? = null,
        open var sourceImage: String? = null,
        open var sourceTitle: String? = null,
        open var shareLink: String = ""
) : RealmObject()