package ru.nbsp.pushka.network.auth

import okhttp3.Interceptor
import okhttp3.Response
import ru.nbsp.pushka.network.service.PushkaAuthService
import javax.inject.Inject

/**
 * Created by Dimorinny on 16.04.16.
 */
class RefreshTokenInterceptor
    @Inject constructor(val accountManager: AccountManager,
                        val authService: PushkaAuthService) : Interceptor {

    companion object {
        private const val TOKEN_HEADER = "Token"
    }

    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        val account = accountManager.getAccount()!!

        if (accountManager.isTokenExpired()) {
            val response = authService.refreshToken(account.accessToken, account.refreshToken).execute().body()
            accountManager.assignAndSaveWithIdentity(response.identity)

            val requestBuilder = request.newBuilder()
                    .header(TOKEN_HEADER, account.accessToken)
                    .method(request.method(), request.body())

            return chain.proceed(requestBuilder.build())
        }

        return chain.proceed(request)
    }
}