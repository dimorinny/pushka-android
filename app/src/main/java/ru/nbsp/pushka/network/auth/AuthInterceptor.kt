package ru.nbsp.pushka.network.auth

import okhttp3.Interceptor
import okhttp3.Response
import ru.nbsp.pushka.network.ApiConfig
import ru.nbsp.pushka.repository.account.AccountRepository
import javax.inject.Inject

/**
 * Created by Dimorinny on 28.02.16.
 */
class AuthInterceptor
    @Inject constructor(val accountRepository: AccountRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        val account = accountRepository.getAccount()

        val requestBuilder = request.newBuilder()
                .header(ApiConfig.TOKEN_HEADER, account!!.accessToken)
                .method(request.method(), request.body())

        return chain.proceed(requestBuilder.build())
    }
}