package ru.nbsp.pushka.api

import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import dagger.Module
import dagger.Provides
import retrofit.RestAdapter
import retrofit.android.AndroidLog
import retrofit.client.OkClient
import retrofit.converter.GsonConverter
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
    fun providePushkaInterface(restAdapter: RestAdapter): PushkaInterface {
        return restAdapter.create(PushkaInterface::class.java)
    }
}