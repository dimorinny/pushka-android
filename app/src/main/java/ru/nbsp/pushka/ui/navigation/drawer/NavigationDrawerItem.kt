package ru.nbsp.pushka.ui.navigation.drawer

import ru.nbsp.pushka.R

/**
 * Created by Dimorinny on 12.02.16.
 */
enum class NavigationDrawerItem(val icon: Int, val title: Int) {

    FEED(R.drawable.ic_home_grey_24dp, R.string.title_feed),
    SUBSCRIPTION(R.drawable.ic_notifications_grey_24dp, R.string.title_subscription),
    DIVIDER(true),
    EXIT(R.drawable.ic_exit_to_app_grey_24dp, R.string.drawer_exit);

    var isDivider: Boolean = false

    constructor(isDivider: Boolean) {
        this.isDivider = isDivider
    }
}
