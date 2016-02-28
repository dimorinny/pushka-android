package ru.nbsp.pushka.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.network.service.PushkaAlertsService
import ru.nbsp.pushka.network.service.PushkaSourceService
import ru.nbsp.pushka.repository.account.AccountRepository
import ru.nbsp.pushka.repository.account.PreferencesAccountRepository
import ru.nbsp.pushka.repository.alert.AlertsRepository
import ru.nbsp.pushka.repository.alert.ServerAlertsRepository
import ru.nbsp.pushka.repository.source.ServerSourcesRepository
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.repository.subscription.FakeSubscriptionsRepository
import ru.nbsp.pushka.repository.subscription.SubscriptionsRepository
import ru.nbsp.pushka.util.SchedulersUtils
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
    fun provideAlertsRepository(apiPushka: PushkaAlertsService, schedulersUtils: SchedulersUtils): AlertsRepository {
        return ServerAlertsRepository(apiPushka, schedulersUtils)
    }

    @Singleton
    @Provides
    fun provideSourcesRepository(apiPushka: PushkaSourceService, schedulersUtils: SchedulersUtils): SourcesRepository {
        return ServerSourcesRepository(apiPushka, schedulersUtils)
    }

    @Singleton
    @Provides
    fun provideSubscriptionsRepository(): SubscriptionsRepository {
        return FakeSubscriptionsRepository()
    }
}