package ru.nbsp.pushka.presentation.subscription.params.control

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.source.control.SimpleListControlItem
import ru.nbsp.pushka.presentation.core.state.State

/**
 * Created by egor on 20.03.16.
 */
interface SimpleListView: BaseView {
    fun setItems(items: List<SimpleListControlItem>)
    fun setState(state: State)
//    fun setToolbarState(state: State)
    open fun returnResult(result: String)
}