package ru.nbsp.pushka.network.error

import android.util.Log
import com.github.salomonbrys.kotson.contains
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import ru.nbsp.pushka.interactor.app.ApplicationInteractor
import ru.nbsp.pushka.network.ApiConfig
import ru.nbsp.pushka.util.ErrorUtils
import javax.inject.Inject

/**
 * Created by Dimorinny on 20.04.16.
 */
class ErrorHandleInterceptor
    @Inject constructor(val applicationInteractor: ApplicationInteractor) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response? {
        val response = chain.proceed(chain.request())

        val parser = JsonParser()
        val bodyString = response.body().string()
        val body = parser.parse(bodyString).asJsonObject
        
        if (body.contains(ApiConfig.ERROR_CODE_KEY) && body.contains(ApiConfig.ERROR_MESSAGE_KEY)) {
            val code = body.get(ApiConfig.ERROR_CODE_KEY).asInt
            val description = body.get(ApiConfig.ERROR_MESSAGE_KEY).asString

            Log.v("ErrorHandleInterceptor", bodyString)

            if (isAuthError(code)) {
                applicationInteractor.logout()
                applicationInteractor.openLoginScreen()
            } else {
                throw ApiErrorException(code, description)
            }
        }

        // Return new body because we cant read response body twice.
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }

    private fun isAuthError(code: Int): Boolean {
        return code == ErrorUtils.AUTH_ERROR_CODE
    }
}