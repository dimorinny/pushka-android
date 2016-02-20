package ru.nbsp.pushka.auth

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.auth.storage.AccountStorageRepository
import ru.nbsp.pushka.auth.storage.PreferencesAccountStorage
import javax.inject.Singleton

/**
 * Created by Dimorinny on 20.02.16.
 */
@Singleton
@Module
class AuthModule {

    @Singleton
    @Provides
    fun provideAccountStorage(gson: Gson, sharedPreferences: SharedPreferences): AccountStorageRepository {
        return PreferencesAccountStorage(gson, sharedPreferences)
    }
}