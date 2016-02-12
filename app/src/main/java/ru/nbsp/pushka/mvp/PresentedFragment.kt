package ru.nbsp.pushka.mvp

import ru.nbsp.pushka.mvp.presenters.BasePresenter
import ru.nbsp.pushka.ui.BaseFragment

/**
 * Created by Dimorinny on 11.02.16.
 */
open class PresentedFragment<T> : BaseFragment() where T : BasePresenter {

    private var presenter: T? = null

    fun initPresenter(presenter: T) {
        this.presenter = presenter
        presenter.onCreate()
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}