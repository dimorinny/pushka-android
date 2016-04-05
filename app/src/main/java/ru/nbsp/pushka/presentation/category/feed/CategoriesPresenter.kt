package ru.nbsp.pushka.presentation.category.feed

import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.source.LoadCategoriesEvent
import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.presentation.core.state.State
import ru.nbsp.pushka.repository.category.CategoriesRepository
import ru.nbsp.pushka.service.ServiceManager
import rx.Observable
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by Dimorinny on 08.03.16.
 */
class CategoriesPresenter
    @Inject constructor(@StorageRepository val storageCategoriesRepository: CategoriesRepository,
                        val rxBus: RxBus,
                        val serviceManager: ServiceManager): BasePresenter {

    override var view: CategoriesView? = null

    val subscription: CompositeSubscription = CompositeSubscription()
    var categories: List<PresentationCategory> = ArrayList()

    override fun onCreate() {
        super.onCreate()

        subscription.add(rxBus.events(LoadCategoriesEvent::class.java)
                .flatMap {
                    when (it) {
                        is LoadCategoriesEvent.Success -> storageCategoriesRepository.getCategories()
                        is LoadCategoriesEvent.Error -> Observable.error(it.t)
                    }
                }.subscribe(LoadCategoriesNetworkSubscriber()))
    }

    fun loadCategoriesFromCache() {
        subscription.add(storageCategoriesRepository.getCategories().subscribe(LoadCategoriesCacheSubscriber()))
    }

    fun loadCategoriesFromServer() {
        view?.setToolbarState(State.STATE_PROGRESS)
        serviceManager.loadCategories()
    }

    fun onCategoryClicked(index: Int) {
        view?.openCategoryScreen(categories[index])
    }

    inner class LoadCategoriesCacheSubscriber : Subscriber<List<PresentationCategory>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()
        }

        override fun onNext(result: List<PresentationCategory>) {
            categories = result

            if (!result.isEmpty()) {
                view?.setState(State.STATE_NORMAL)
            }

            view?.setCategories(result)
        }
    }

    inner class LoadCategoriesNetworkSubscriber : Subscriber<List<PresentationCategory>>() {
        override fun onCompleted() {}

        override fun onError(t: Throwable) {
            t.printStackTrace()

            if (categories.size == 0) {
                view?.setState(State.STATE_ERROR)
                view?.setToolbarState(State.STATE_NORMAL)
            } else {
                view?.setToolbarState(State.STATE_ERROR)
            }
        }

        override fun onNext(result: List<PresentationCategory>) {
            categories = result
            view?.setToolbarState(State.STATE_NORMAL)
            view?.setState(if (result.isEmpty()) State.STATE_EMPTY else State.STATE_NORMAL)
            view?.setCategories(result)
        }
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }
}