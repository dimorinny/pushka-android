package ru.nbsp.pushka.iteractor.user

import ru.nbsp.pushka.api.PushkaInterface
import ru.nbsp.pushka.api.response.LoginResponse
import ru.nbsp.pushka.util.SchedulersManager
import rx.Observable

/**
 * Created by Dimorinny on 21.02.16.
 */
class ApiUserIteractor(val apiPushka: PushkaInterface, val schedulersManager: SchedulersManager): UserIteractor {

    override fun login(provider: String, token: String): Observable<LoginResponse> {
        return apiPushka.login(provider, token)
                .compose(schedulersManager.applySchedulers<LoginResponse>())
    }
}