package ru.nbsp.pushka.presentation

import ru.nbsp.pushka.presentation.base.BaseActivity
import ru.nbsp.pushka.presentation.base.BasePresenter

/**
 * Created by Dimorinny on 11.02.16.
 */
open class PresentedActivity<T> : BaseActivity() where T : BasePresenter {

    private var presenter: T? = null

    open fun initPresenter(presenter: T) {
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