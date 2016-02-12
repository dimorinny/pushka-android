package ru.nbsp.pushka.ui.navigation

import android.support.v4.app.Fragment
import ru.nbsp.pushka.R
import ru.nbsp.pushka.ui.feed.FeedFragment

/**
 * Created by Dimorinny on 12.02.16.
 */
enum class NavigationDrawerItem(val icon: Int,
                                val title: Int,
                                val fragment: Class<out Fragment>) {

    FEED(R.mipmap.ic_launcher, R.string.app_name, FeedFragment::class.java);

    var isDivider: Boolean = false

    constructor(isDivider: Boolean) {
        this.isDivider = isDivider
    }
}