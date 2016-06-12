package ru.nbsp.pushka.network

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.nbsp.pushka.BuildConfig
import ru.nbsp.pushka.di.annotation.AuthRequired
import ru.nbsp.pushka.network.auth.AuthInterceptor
import ru.nbsp.pushka.network.auth.RefreshTokenInterceptor
import ru.nbsp.pushka.network.error.ErrorHandleInterceptor
import ru.nbsp.pushka.network.service.*
import javax.inject.Singleton

/**
 * Created by Dimorinny on 12.02.16.
 */
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor();
        interceptor.level = HttpLoggingInterceptor.Level.BODY;
        return interceptor
    }

    @Singleton
    @Provides
    fun provideClient(loggingInterceptor: HttpLoggingInterceptor, errorHandleInterceptor: ErrorHandleInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(errorHandleInterceptor)
                .build()
    }

    @Singleton
    @Provides
    @AuthRequired
    fun provideAuthRequiredClient(tokenInterceptor: AuthInterceptor, refreshTokenInterceptor: RefreshTokenInterceptor, loggingInterceptor: HttpLoggingInterceptor, errorHandleInterceptor: ErrorHandleInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(tokenInterceptor)
                .addInterceptor(refreshTokenInterceptor)
                .addInterceptor(errorHandleInterceptor)
                .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideRestAdapter(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(if (BuildConfig.DEBUG) ApiConfig.DEV_URL else ApiConfig.PROD_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Singleton
    @Provides
    @AuthRequired
    fun provideAuthRequiredRestAdapter(@AuthRequired client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(if (BuildConfig.DEBUG) ApiConfig.DEV_URL else ApiConfig.PROD_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Singleton
    @Provides
    fun providePushkaAuthService(retrofit: Retrofit): PushkaAuthService {
        return retrofit.create(PushkaAuthService::class.java)
    }

    @Singleton
    @Provides
    fun providePushkaSourceService(@AuthRequired retrofit: Retrofit): PushkaSourceService {
        return retrofit.create(PushkaSourceService::class.java)
    }

    @Singleton
    @Provides
    fun providePushkaAlertsService(@AuthRequired retrofit: Retrofit): PushkaAlertsService {
        return retrofit.create(PushkaAlertsService::class.java)
    }

    @Singleton
    @Provides
    fun providePushkaSubscriptionService(@AuthRequired retrofit: Retrofit): PushkaSubscriptionService {
        return retrofit.create(PushkaSubscriptionService::class.java)
    }

    @Singleton
    @Provides
    fun providePushkaDeviceService(@AuthRequired retrofit: Retrofit): PushkaDeviceService {
        return retrofit.create(PushkaDeviceService::class.java)
    }

    @Singleton
    @Provides
    fun providePicasso(context: Context): Picasso {
        return Picasso.Builder(context)
                .downloader(OkHttp3Downloader(context, 1024 * 1024 * 25))
                .build()
    }
}