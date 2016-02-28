package ru.nbsp.pushka.network.auth

import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.Response
import ru.nbsp.pushka.repository.account.AccountRepository
import javax.inject.Inject

/**
 * Created by Dimorinny on 28.02.16.
 */
class AuthInterceptor
    @Inject constructor(val accountRepository: AccountRepository) : Interceptor {

    companion object {
        private const val TOKEN_HEADER = "Token"
    }

    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        val account = accountRepository.getAccount()

        val requestBuilder = request.newBuilder()
                .header(TOKEN_HEADER, account!!.accessToken)
                .method(request.method(), request.body())

        return chain.proceed(requestBuilder.build())
    }
}