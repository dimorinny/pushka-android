package ru.nbsp.pushka.presentation.category.feed

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.presentation.core.state.State

/**
 * Created by Dimorinny on 08.03.16.
 */
interface CategoriesView : BaseView {
    fun setCategories(categories: List<PresentationCategory>)
    fun openCategoryScreen(presentationCategory: PresentationCategory)
    fun setState(state: State)
    fun setToolbarState(state: State)
}