package ru.nbsp.pushka.network

import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import dagger.Module
import dagger.Provides
import retrofit.RestAdapter
import retrofit.android.AndroidLog
import retrofit.client.OkClient
import retrofit.converter.GsonConverter
import ru.nbsp.pushka.annotation.AuthRequired
import ru.nbsp.pushka.network.auth.AuthInterceptor
import ru.nbsp.pushka.network.service.PushkaAlertsService
import ru.nbsp.pushka.network.service.PushkaAuthService
import ru.nbsp.pushka.network.service.PushkaSourceService
import javax.inject.Singleton

/**
 * Created by Dimorinny on 12.02.16.
 */
@Singleton
@Module
class ApiModule {

    companion object {
        private const val BASE_URL = "http://104.155.30.211/api/v1"
        private const val LOG_TAG = "RETROFIT"
    }

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Singleton
    @Provides
    @AuthRequired
    fun provideAuthRequiredClient(interceptor: AuthInterceptor): OkHttpClient {
        val client = OkHttpClient()
        client.interceptors().add(interceptor)
        return client
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideRestAdapter(client: OkHttpClient, gson: Gson): RestAdapter {
        return RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(OkClient(client))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(AndroidLog(LOG_TAG))
                .setConverter(GsonConverter(gson))
                .build()
    }

    @Singleton
    @Provides
    @AuthRequired
    fun provideAuthRequiredRestAdapter(@AuthRequired client: OkHttpClient, gson: Gson): RestAdapter {
        return RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(OkClient(client))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(AndroidLog(LOG_TAG))
                .setConverter(GsonConverter(gson))
                .build()
    }

    @Singleton
    @Provides
    fun providePushkaAuthService(restAdapter: RestAdapter): PushkaAuthService {
        return restAdapter.create(PushkaAuthService::class.java)
    }

    @Singleton
    @Provides
    fun providePushkaSourceService(@AuthRequired restAdapter: RestAdapter): PushkaSourceService {
        return restAdapter.create(PushkaSourceService::class.java)
    }

    @Singleton
    @Provides
    fun providePushkaAlertsService(@AuthRequired restAdapter: RestAdapter): PushkaAlertsService {
        return restAdapter.create(PushkaAlertsService::class.java)
    }
}