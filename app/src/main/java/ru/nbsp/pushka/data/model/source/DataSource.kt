package ru.nbsp.pushka.data.model.source

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by Dimorinny on 26.02.16.
 */
open class DataSource(
        open var id: String = "",
        open var params: RealmList<DataParam> = RealmList(),
        open var name: String = "",
        open var description: String = "",
        open var category: String = ""
) : RealmObject()