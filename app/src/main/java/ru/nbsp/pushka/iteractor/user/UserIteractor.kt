package ru.nbsp.pushka.iteractor.user

import ru.nbsp.pushka.api.response.LoginResponse
import rx.Observable

/**
 * Created by Dimorinny on 21.02.16.
 */
interface UserIteractor {
    fun login(provider: String, token: String): Observable<LoginResponse>
}