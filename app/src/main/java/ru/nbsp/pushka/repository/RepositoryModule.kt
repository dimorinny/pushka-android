package ru.nbsp.pushka.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.repository.account.AccountRepository
import ru.nbsp.pushka.repository.account.PreferencesAccountRepository
import ru.nbsp.pushka.repository.alert.AlertsRepository
import ru.nbsp.pushka.repository.alert.FakeAlertsRepository
import ru.nbsp.pushka.repository.source.FakeSourcesRepository
import ru.nbsp.pushka.repository.source.SourcesRepository
import javax.inject.Singleton

/**
 * Created by Dimorinny on 21.02.16.
 */
@Singleton
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAccountRepository(gson: Gson, sharedPreferences: SharedPreferences): AccountRepository {
        return PreferencesAccountRepository(gson, sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideAlertsRepository(): AlertsRepository {
        return FakeAlertsRepository()
    }

    @Singleton
    @Provides
    fun provideSourcesRepository(): SourcesRepository{
        return FakeSourcesRepository()
    }
}