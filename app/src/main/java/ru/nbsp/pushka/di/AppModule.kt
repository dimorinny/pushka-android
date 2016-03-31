package ru.nbsp.pushka.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.telephony.TelephonyManager
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.BaseApplication
import javax.inject.Singleton

/**
 * Created by Dimorinny on 11.02.16.
 */

@Singleton
@Module
class AppModule(val application: BaseApplication) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun provideTelephonyManager(context: Context): TelephonyManager {
        return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    @Singleton
    @Provides
    fun provideWindowManager(context: Context): WindowManager {
        return context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
}