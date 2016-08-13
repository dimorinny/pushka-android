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
            "newspaper" to R.drawable.ic_newspaper_white_36dp,
            "filmstrip" to R.drawable.ic_filmstrip_white_36dp,
            "habrahabr" to R.drawable.ic_habrahabr_white_36dp,
            "geektimes" to R.drawable.ic_geektimes_white_36dp,
            "megamozg" to R.drawable.ic_megamozg_white_36dp,
            "humor" to R.drawable.ic_smile_white_36dp,
            "livejournal" to R.drawable.ic_livejournal_white_36dp,
            "twitch" to R.drawable.ic_twitch_white_36dp,
            "twitter" to R.drawable.ic_twitter_36dp,
            "android" to R.drawable.ic_android_white_24dp,
            "telegram" to R.drawable.ic_telegram_white_24dp,
            "rutracker" to R.drawable.ic_rutracker_white_36dp,
            "calendar" to R.drawable.ic_calendar_white_36dp,
            "category_news" to R.drawable.ic_news_112dp,
            "category_films" to R.drawable.ic_films_112dp,
            "category_social" to R.drawable.ic_bullhorn_112dp,
            "category_games" to R.drawable.ic_gamepad_112dp,
            "category_it" to R.drawable.ic_listing_112dp,
            "category_utilities" to R.drawable.ic_utilities_112dp
    )

    fun getIcon(name: String): Int {
        return if (icons.containsKey(name)) icons[name]!! else R.drawable.ic_info_white_36dp
    }
}