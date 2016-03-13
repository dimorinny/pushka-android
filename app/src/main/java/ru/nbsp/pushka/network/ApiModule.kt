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
import ru.nbsp.pushka.annotation.AuthRequired
import ru.nbsp.pushka.network.auth.AuthInterceptor
import ru.nbsp.pushka.network.service.PushkaAlertsService
import ru.nbsp.pushka.network.service.PushkaAuthService
import ru.nbsp.pushka.network.service.PushkaSourceService
import ru.nbsp.pushka.network.service.PushkaSubscriptionService
import javax.inject.Singleton

/**
 * Created by Dimorinny on 12.02.16.
 */
@Singleton
@Module
class ApiModule {

    companion object {
        private const val BASE_URL = "http://130.211.103.133/api/v1/"
        private const val LOG_TAG = "RETROFIT"
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor();
        interceptor.level = HttpLoggingInterceptor.Level.BODY;
        return interceptor
    }

    @Singleton
    @Provides
    fun provideClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Singleton
    @Provides
    @AuthRequired
    fun provideAuthRequiredClient(interceptor: AuthInterceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
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
                .baseUrl(BASE_URL)
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
                .baseUrl(BASE_URL)
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
    fun providePicasso(context: Context): Picasso {
        return Picasso.Builder(context)
                .downloader(OkHttp3Downloader(context, 1024 * 1024 * 25))
                .build()
    }
}