package ru.nbsp.pushka.presentation.core.base

/**
 * Created by Dimorinny on 11.02.16.
 */

interface BasePresenter {
    val view: BaseView?

    open fun onCreate() {}

    open fun onPause() {}

    open fun onResume() {}

    open fun onDestroy() {}
}