package ru.nbsp.pushka.gcm

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.gcm.manage.GcmManager
import ru.nbsp.pushka.gcm.manage.GcmManagerImpl
import javax.inject.Singleton

/**
 * Created by Dimorinny on 31.03.16.
 */
@Singleton
@Module
class GcmModule() {

    @Singleton
    @Provides
    fun provideGcmManager(context: Context): GcmManager {
        return GcmManagerImpl(context)
    }
}