package ru.nbsp.pushka.service

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Dimorinny on 12.02.16.
 */
@Singleton
@Module
class ServiceModule {

    @Singleton
    @Provides
    fun providePushkaServiceHelper(context: Context): PushkaServiceHelper {
        return PushkaServiceHelper(context)
    }
}