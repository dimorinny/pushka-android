package ru.nbsp.pushka.util

import ru.nbsp.pushka.R
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 28.03.16.
 */
@Singleton
class SourceIconUtils @Inject constructor() {

    private val icons = mapOf(
            Pair("newspaper", R.drawable.ic_newspaper_white_36dp),
            Pair("filmstrip", R.drawable.ic_filmstrip_white_36dp),
            Pair("android", R.drawable.ic_android_white_24dp),
            Pair("telegram", R.drawable.ic_telegram_white_24dp)
    )

    fun getIcon(name: String): Int {
        return icons[name]!!
    }
}