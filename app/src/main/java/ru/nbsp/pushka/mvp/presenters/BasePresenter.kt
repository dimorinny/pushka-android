package ru.nbsp.pushka.mvp.presenters

import ru.nbsp.pushka.mvp.views.BaseView

/**
 * Created by Dimorinny on 11.02.16.
 */

interface BasePresenter {
    val view: BaseView?

    open fun onCreate()

    open fun onPause()

    open fun onResume()

    open fun onDestroy()
}