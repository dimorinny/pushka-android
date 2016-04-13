package ru.nbsp.pushka.presentation.subscription.params.control.autocomplete

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.subscription.PresentationListItem

/**
 * Created by Dimorinny on 12.04.16.
 */
interface AutoCompleteControlView : BaseView {
    fun setItems(items: List<PresentationListItem>)
}