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
            Pair("livejournal", R.drawable.ic_livejournal_white_36dp),
            Pair("twitch", R.drawable.ic_twitch_white_36dp),
            Pair("android", R.drawable.ic_android_white_24dp),
            Pair("telegram", R.drawable.ic_telegram_white_24dp),
            Pair("category_news", R.drawable.ic_news_112dp),
            Pair("category_films", R.drawable.ic_films_112dp),
            Pair("category_social", R.drawable.ic_bullhorn_112dp),
            Pair("category_games", R.drawable.ic_gamepad_112dp),
            Pair("category_it", R.drawable.ic_listing_112dp)
    )

    fun getIcon(name: String): Int {
        return if (icons.containsKey(name)) icons[name]!! else R.drawable.ic_info_white_36dp
    }
}