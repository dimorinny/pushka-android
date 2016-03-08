package ru.nbsp.pushka.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.annotation.ApiRepository
import ru.nbsp.pushka.annotation.StorageRepository
import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.presentation.alert.PresentationAlertMapper
import ru.nbsp.pushka.mapper.presentation.source.PresentationSourceMapper
import ru.nbsp.pushka.network.service.PushkaAlertsService
import ru.nbsp.pushka.network.service.PushkaSourceService
import ru.nbsp.pushka.repository.account.AccountRepository
import ru.nbsp.pushka.repository.account.PreferencesAccountRepository
import ru.nbsp.pushka.repository.alert.AlertsRepository
import ru.nbsp.pushka.repository.alert.ApiAlertsRepository
import ru.nbsp.pushka.repository.alert.StorageAlertsRepository
import ru.nbsp.pushka.repository.category.CategoriesRepository
import ru.nbsp.pushka.repository.category.FakeCategoriesRepository
import ru.nbsp.pushka.repository.source.ServerSourcesRepository
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.repository.source.StorageSourcesRepository
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

    @ApiRepository
    @Singleton
    @Provides
    fun provideApiAlertsRepository(apiPushka: PushkaAlertsService, alertMapper: PresentationAlertMapper, schedulersUtils: SchedulersUtils): AlertsRepository {
        return ApiAlertsRepository(apiPushka, alertMapper, schedulersUtils)
    }

    @StorageRepository
    @Singleton
    @Provides
    fun provideStorageAlertsRepository(dataManager: DataManager, alertMapper: PresentationAlertMapper): AlertsRepository {
        return StorageAlertsRepository(dataManager, alertMapper)
    }

    @ApiRepository
    @Singleton
    @Provides
    fun provideApiSourcesRepository(apiPushka: PushkaSourceService, presentationSourceMapper: PresentationSourceMapper, schedulersUtils: SchedulersUtils): SourcesRepository {
        return ServerSourcesRepository(apiPushka, presentationSourceMapper, schedulersUtils)
    }

    @StorageRepository
    @Singleton
    @Provides
    fun provideStorageSourcesRepository(dataManager: DataManager, presentationSourceMapper: PresentationSourceMapper): SourcesRepository {
        return StorageSourcesRepository(dataManager, presentationSourceMapper)
    }

    @Singleton
    @Provides
    fun provideSubscriptionsRepository(): SubscriptionsRepository {
        return FakeSubscriptionsRepository()
    }

    @Singleton
    @Provides
    fun provideCategoriesRepository(): CategoriesRepository {
        return FakeCategoriesRepository()
    }
}