package ru.nbsp.pushka.data.table

/**
 * Created by Dimorinny on 02.03.16.
 */
class AlertTable private constructor() {
    companion object {
        const val TABLE = "Alert"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_TEXT = "text"
        const val COLUMN_PHOTO = "photo"
        const val COLUMN_SOURCE_IMAGE = "source_image"
        const val COLUMN_SOURCE_TITLE = "source_title"
        const val COLUMN_SHARE_LINK = "share_link"

        fun getCreateTableQuery() : String {
            return "CREATE TABLE $TABLE (" +
                    "$COLUMN_ID TEXT NOT NULL PRIMARY KEY," +
                    "$COLUMN_TITLE TEXT NOT NULL," +
                    "$COLUMN_TEXT TEXT NOT NULL," +
                    "$COLUMN_PHOTO TEXT," +
                    "$COLUMN_SOURCE_IMAGE TEXT NOT NULL," +
                    "$COLUMN_SOURCE_TITLE TEXT NOT NULL," +
                    "$COLUMN_SHARE_LINK TEXT NOT NULL" +
                    ");"
        }
    }
}

//data class Alert(
//        @SerializedName("title") val title: String,
//        @SerializedName("description") val text: String,
//        @SerializedName("thumbnail_url") val photo: String?,
//        val sourceImage: String,
//        val sourceTitle: String,
//        @SerializedName("url") val shareLink: String)
