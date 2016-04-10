package ru.nbsp.pushka.util

import ru.nbsp.pushka.R
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 28.03.16.
 */
@Singleton
class IconUtils @Inject constructor() {

    private val icons = mapOf(
            Pair("newspaper", R.drawable.ic_newspaper_white_36dp),
            Pair("filmstrip", R.drawable.ic_filmstrip_white_36dp),
            Pair("habrahabr", R.drawable.ic_habrahabr_white_36dp),
            Pair("geektimes", R.drawable.ic_geektimes_white_36dp),
            Pair("megamozg", R.drawable.ic_megamozg_white_36dp),
            Pair("humor", R.drawable.ic_smile_white_36dp),
            Pair("android", R.drawable.ic_android_white_24dp),
            Pair("telegram", R.drawable.ic_telegram_white_24dp),
            Pair("category_news", R.drawable.ic_news_112dp),
            Pair("category_films", R.drawable.ic_films_112dp)
    )

    fun getIcon(name: String): Int {
        return icons[name]!!
    }
}