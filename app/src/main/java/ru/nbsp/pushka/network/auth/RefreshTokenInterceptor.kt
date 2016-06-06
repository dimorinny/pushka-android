package ru.nbsp.pushka.network.auth

import com.github.salomonbrys.kotson.contains
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.Response
import ru.nbsp.pushka.interactor.app.ApplicationInteractor
import ru.nbsp.pushka.network.ApiConfig
import ru.nbsp.pushka.network.response.RefreshTokenResponse
import ru.nbsp.pushka.network.service.PushkaAuthService
import ru.nbsp.pushka.util.ErrorUtils
import javax.inject.Inject

/**
 * Created by Dimorinny on 16.04.16.
 */
class RefreshTokenInterceptor
    @Inject constructor(val accountManager: AccountManager,
                        val authService: PushkaAuthService,
                        val gson: Gson,
                        val applicationInteractor: ApplicationInteractor) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        var account = accountManager.getAccount()!!

        if (accountManager.isTokenExpired()) {
            val body = authService.refreshToken(account.accessToken, account.refreshToken).execute().body()

            if (!isAuthError(body)) {
                val refreshTokenResponse = parseRefreshTokenResponse(body)
                account = accountManager.assignAndSaveWithIdentity(refreshTokenResponse.identity)

                val requestBuilder = request.newBuilder()
                        .header(ApiConfig.TOKEN_HEADER, account.accessToken)
                        .method(request.method(), request.body())

                return chain.proceed(requestBuilder.build())
            } else {
                applicationInteractor.logout()
                applicationInteractor.openLoginScreen()
            }
        }

        return chain.proceed(request)
    }

    private fun isAuthError(body: JsonObject): Boolean {
        return body.contains(ApiConfig.ERROR_CODE_KEY) && body.get(ApiConfig.ERROR_CODE_KEY).asInt == ErrorUtils.AUTH_ERROR_CODE
    }

    private fun parseRefreshTokenResponse(body: JsonObject): RefreshTokenResponse {
        return gson.fromJson<RefreshTokenResponse>(body)
    }
}