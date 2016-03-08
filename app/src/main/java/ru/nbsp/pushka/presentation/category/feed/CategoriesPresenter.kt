package ru.nbsp.pushka.presentation.category.feed

import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.repository.category.CategoriesRepository
import rx.Subscriber
import javax.inject.Inject

/**
 * Created by Dimorinny on 08.03.16.
 */
class CategoriesPresenter
    @Inject constructor(val categoriesRepository: CategoriesRepository): BasePresenter {

    override var view: CategoriesView? = null

    fun loadCategoriesFromCache() {
        categoriesRepository.getCategories().subscribe(LoadCategoriesSubscriber())
    }

    fun onCategoryClicked(index: Int) {
        view?.openCategoryScreen()
    }

    inner class LoadCategoriesSubscriber : Subscriber<List<PresentationCategory>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(categories: List<PresentationCategory>) {
            view?.setCategories(categories)
            view?.setState(if (categories.isEmpty()) State.STATE_EMPTY else State.STATE_NORMAL)
        }
    }
}