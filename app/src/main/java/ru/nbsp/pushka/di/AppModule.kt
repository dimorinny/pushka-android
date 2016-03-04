package ru.nbsp.pushka.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
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
}