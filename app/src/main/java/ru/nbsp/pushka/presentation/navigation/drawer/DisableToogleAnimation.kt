package ru.nbsp.pushka.presentation.navigation.drawer

import android.app.Activity
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.View
import ru.nbsp.pushka.R

/**
 * Created by Dimorinny on 22.02.16.
 */
class DisableToogleAnimation(activity: Activity?,
                             drawerLayout: DrawerLayout,
                             toolbar: Toolbar?)

    : ActionBarDrawerToggle(activity, drawerLayout, toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
    
    override fun onDrawerSlide(drawerView: View?, slideOffset: Float)
            = super.onDrawerSlide(drawerView, 0F)
}