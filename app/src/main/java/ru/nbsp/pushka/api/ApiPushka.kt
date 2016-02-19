package ru.nbsp.pushka.api

import android.content.Context
import com.squareup.okhttp.OkHttpClient
import retrofit.RestAdapter
import retrofit.android.AndroidLog
import retrofit.client.OkClient
import ru.nbsp.pushka.api.response.LoginResponse
import rx.Observable

/**
 * Created by Dimorinny on 12.02.16.
 */
class ApiPushka(private val context: Context, private val client: OkHttpClient) {

    companion object {
        private val BASE_URL = "http://104.155.30.211/api/v1"
    }

    private val restAdapter: RestAdapter = initRestAdapter()
    private val restInterface : PushkaInterface = restAdapter.create(PushkaInterface::class.java)

    private fun initRestAdapter() : RestAdapter {
        return RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(OkClient(client))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(AndroidLog("RETROFIT"))
                .build()
    }

    fun login(provider: String, token: String): Observable<LoginResponse> {
        return restInterface.login(provider, token)
    }
}