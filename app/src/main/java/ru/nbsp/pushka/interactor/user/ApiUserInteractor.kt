package ru.nbsp.pushka.interactor.user

import ru.nbsp.pushka.network.response.LoginResponse
import ru.nbsp.pushka.network.service.PushkaAuthService
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable

/**
 * Created by Dimorinny on 21.02.16.
 */
class ApiUserInteractor(
        val apiPushka: PushkaAuthService,
        val schedulersUtils: SchedulersUtils): UserInteractor {

    override fun login(provider: String, token: String): Observable<LoginResponse> {
        return apiPushka.login(provider, token)
                .compose(schedulersUtils.applySchedulers<LoginResponse>())
    }
}