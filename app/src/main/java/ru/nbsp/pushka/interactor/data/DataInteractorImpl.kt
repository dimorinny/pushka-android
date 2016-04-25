package ru.nbsp.pushka.interactor.data

import ru.nbsp.pushka.data.DataManager
import rx.Observable

/**
 * Created by Dimorinny on 26.04.16.
 */
class DataInteractorImpl(val dataManager: DataManager) : DataInteractor {
    override fun clearAll(): Observable<Any> {
        return Observable.just(Any())
                .doOnNext { dataManager.clearAll() }
    }
}