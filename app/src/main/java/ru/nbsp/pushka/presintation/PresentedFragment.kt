package ru.nbsp.pushka.presintation

import ru.nbsp.pushka.presintation.BasePresenter

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