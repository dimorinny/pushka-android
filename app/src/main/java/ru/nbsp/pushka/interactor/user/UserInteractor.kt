package ru.nbsp.pushka.interactor.user

import ru.nbsp.pushka.network.response.LoginResponse
import rx.Observable

/**
 * Created by Dimorinny on 21.02.16.
 */
interface UserInteractor {
    fun login(provider: String, token: String): Observable<LoginResponse>
}