package ru.nbsp.pushka.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.di.annotation.ApiRepository
import ru.nbsp.pushka.di.annotation.StorageRepository
import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.mapper.presentation.alert.PresentationAlertMapper
import ru.nbsp.pushka.mapper.presentation.device.PresentationDeviceMapper
import ru.nbsp.pushka.mapper.presentation.source.PresentationCategoryMapper
import ru.nbsp.pushka.mapper.presentation.source.PresentationSourceMapper
import ru.nbsp.pushka.mapper.presentation.subscription.PresentationListItemMapper
import ru.nbsp.pushka.mapper.presentation.subscription.PresentationSubscriptionMapper
import ru.nbsp.pushka.network.service.PushkaAlertsService
import ru.nbsp.pushka.network.service.PushkaDeviceService
import ru.nbsp.pushka.network.service.PushkaSourceService
import ru.nbsp.pushka.network.service.PushkaSubscriptionService
import ru.nbsp.pushka.repository.account.AccountRepository
import ru.nbsp.pushka.repository.account.PreferencesAccountRepository
import ru.nbsp.pushka.repository.alert.AlertsRepository
import ru.nbsp.pushka.repository.alert.ApiAlertsRepository
import ru.nbsp.pushka.repository.alert.StorageAlertsRepository
import ru.nbsp.pushka.repository.category.ApiCategoriesRepository
import ru.nbsp.pushka.repository.category.CategoriesRepository
import ru.nbsp.pushka.repository.category.StorageCategoriesRepository
import ru.nbsp.pushka.repository.device.ApiDeviceRepository
import ru.nbsp.pushka.repository.device.DeviceRepository
import ru.nbsp.pushka.repository.device.StorageDeviceRepository
import ru.nbsp.pushka.repository.source.ServerSourcesRepository
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.repository.source.StorageSourcesRepository
import ru.nbsp.pushka.repository.subscription.*
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

    @ApiRepository
    @Singleton
    @Provides
    fun provideApiSubscriptionsRepository(apiPushka: PushkaSubscriptionService, subscriptionMapper: PresentationSubscriptionMapper, schedulersUtils: SchedulersUtils): SubscriptionRepository {
        return ApiSubscriptionRepository(apiPushka, subscriptionMapper, schedulersUtils)
    }

    @StorageRepository
    @Singleton
    @Provides
    fun provideStorageSubscriptionRepository(dataManager: DataManager, presentationSubscriptionMapper: PresentationSubscriptionMapper): SubscriptionRepository {
        return StorageSubscriptionRepository(dataManager, presentationSubscriptionMapper)
    }

    @ApiRepository
    @Singleton
    @Provides
    fun provideApiCategoriesRepository(apiPushka: PushkaSourceService, presentationCategoryMapper: PresentationCategoryMapper, schedulersUtils: SchedulersUtils): CategoriesRepository {
        return ApiCategoriesRepository(apiPushka, presentationCategoryMapper, schedulersUtils)
    }

    @StorageRepository
    @Singleton
    @Provides
    fun provideStorageCategoriesRepository(dataManager: DataManager, presentationCategoryMapper: PresentationCategoryMapper): CategoriesRepository {
        return StorageCategoriesRepository(dataManager, presentationCategoryMapper)
    }

    @ApiRepository
    @Singleton
    @Provides
    fun provideApiDevicesRepository(api: PushkaDeviceService, presentationDeviceMapper: PresentationDeviceMapper, schedulersUtils: SchedulersUtils): DeviceRepository {
        return ApiDeviceRepository(api, presentationDeviceMapper, schedulersUtils)
    }

    @StorageRepository
    @Singleton
    @Provides
    fun provideStorageDevicesRepository(dataManager: DataManager, presentationDeviceMapper: PresentationDeviceMapper): DeviceRepository {
        return StorageDeviceRepository(dataManager, presentationDeviceMapper)
    }

    @ApiRepository
    @Singleton
    @Provides
    fun provideApiListItemRepository(apiPushka: PushkaSubscriptionService, listItemMapper: PresentationListItemMapper, schedulersUtils: SchedulersUtils): ListItemRepository {
        return ApiListItemRepository(apiPushka, listItemMapper, schedulersUtils)
    }
}