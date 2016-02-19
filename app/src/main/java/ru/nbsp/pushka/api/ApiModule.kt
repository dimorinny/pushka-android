package ru.nbsp.pushka.api

import android.content.Context
import com.squareup.okhttp.OkHttpClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Dimorinny on 12.02.16.
 */
@Singleton
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Singleton
    @Provides
    fun providePushkaApi(context: Context, client: OkHttpClient) : ApiPushka {
        return ApiPushka(context, client)
    }
}