package ru.nbsp.pushka.data.model.alert

import com.google.gson.annotations.SerializedName
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType
import ru.nbsp.pushka.data.table.AlertTable

/**
 * Created by Dimorinny on 25.02.16.
 */
@StorIOSQLiteType(table = AlertTable.TABLE)
data class Alert(

        @SerializedName("_id")
        @StorIOSQLiteColumn(name = AlertTable.COLUMN_ID, key = true)
        @JvmField
        var id: String = "",

        @SerializedName("title")
        @StorIOSQLiteColumn(name = AlertTable.COLUMN_TITLE)
        @JvmField
        var title: String = "",

        @SerializedName("description")
        @StorIOSQLiteColumn(name = AlertTable.COLUMN_TEXT)
        @JvmField
        var text: String = "",

        @SerializedName("thumbnail_url")
        @StorIOSQLiteColumn(name = AlertTable.COLUMN_PHOTO)
        @JvmField
        var photo: String? = null,

        @StorIOSQLiteColumn(name = AlertTable.COLUMN_SOURCE_IMAGE)
        @JvmField
        var sourceImage: String = "",

        @StorIOSQLiteColumn(name = AlertTable.COLUMN_SOURCE_TITLE)
        @JvmField
        var sourceTitle: String = "",

        @SerializedName("url")
        @StorIOSQLiteColumn(name = AlertTable.COLUMN_SHARE_LINK)
        @JvmField
        var shareLink: String = ""
)