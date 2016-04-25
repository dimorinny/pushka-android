package ru.nbsp.pushka.interactor.data

import rx.Observable

/**
 * Created by Dimorinny on 26.04.16.
 */
interface DataInteractor {
    fun clearAll(): Observable<Any>
}