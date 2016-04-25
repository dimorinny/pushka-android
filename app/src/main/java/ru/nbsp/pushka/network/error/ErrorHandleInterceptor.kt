package ru.nbsp.pushka.network.error

import android.util.Log
import com.github.salomonbrys.kotson.contains
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Created by Dimorinny on 20.04.16.
 */
class ErrorHandleInterceptor
    @Inject constructor() : Interceptor {

    companion object {
        private const val ERROR_CODE_KEY = "error"
        private const val ERROR_MESSAGE_KEY = "description"
    }

    override fun intercept(chain: Interceptor.Chain): Response? {
        val response = chain.proceed(chain.request())

        val parser = JsonParser()
        val bodyString = response.body().string()
        val body = parser.parse(bodyString).asJsonObject


        if (body.contains(ERROR_CODE_KEY) && body.contains(ERROR_MESSAGE_KEY)) {
            Log.v("Error", bodyString)
            throw ApiErrorException(
                    code = body.get(ERROR_CODE_KEY).asInt,
                    description = body.get(ERROR_MESSAGE_KEY).asString)
        }

        // Return new body because we cant read response body twice.
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }
}