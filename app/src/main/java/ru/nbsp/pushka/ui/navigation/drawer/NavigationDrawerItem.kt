package ru.nbsp.pushka.ui.navigation.drawer

import ru.nbsp.pushka.R

/**
 * Created by Dimorinny on 12.02.16.
 */
enum class NavigationDrawerItem(val icon: Int, val title: Int) {

    FEED(R.mipmap.ic_launcher, R.string.app_name);

    var isDivider: Boolean = false

    constructor(isDivider: Boolean) {
        this.isDivider = isDivider
    }
}