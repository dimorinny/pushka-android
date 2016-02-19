package ru.nbsp.pushka.iteractor.login

import ru.nbsp.pushka.api.ApiPushka
import ru.nbsp.pushka.api.response.LoginResponse
import ru.nbsp.pushka.di.SchedulerModule
import ru.nbsp.pushka.iteractor.BaseIteractor
import ru.nbsp.pushka.iteractor.login.param.LoginParam
import rx.Observable
import rx.Scheduler
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Dimorinny on 17.02.16.
 */
class LoginIteractor @Inject constructor(
        @Named(SchedulerModule.IO) jobScheduler: Scheduler,
        @Named(SchedulerModule.UI) resultScheduler: Scheduler,
        val api: ApiPushka) : BaseIteractor<LoginResponse, LoginParam>(jobScheduler, resultScheduler) {

    // Warning
    override fun buildObservable(parameter: LoginParam?): Observable<LoginResponse> {
        return api.login(parameter!!.provider, parameter.token)
    }
}