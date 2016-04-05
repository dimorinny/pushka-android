package ru.nbsp.pushka.interactor

import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.interactor.alert.StorageAlertInteractor
import ru.nbsp.pushka.interactor.alert.StorageAlertInteractorImpl
import ru.nbsp.pushka.interactor.category.StorageCategoryInteractor
import ru.nbsp.pushka.interactor.category.StorageCategoryInteractorImpl
import ru.nbsp.pushka.interactor.device.ApiDeviceInteractor
import ru.nbsp.pushka.interactor.device.ApiDeviceInteractorImpl
import ru.nbsp.pushka.interactor.source.StorageSourceInteractor
import ru.nbsp.pushka.interactor.source.StorageSourceInteractorImpl
import ru.nbsp.pushka.interactor.subscription.ApiSubscriptionInteractor
import ru.nbsp.pushka.interactor.subscription.ApiSubscriptionInteractorImpl
import ru.nbsp.pushka.interactor.subscription.StorageSubscriptionInteractor
import ru.nbsp.pushka.interactor.subscription.StorageSubscriptionInteractorImpl
import ru.nbsp.pushka.interactor.user.ApiUserInteractor
import ru.nbsp.pushka.interactor.user.UserInteractor
import ru.nbsp.pushka.mapper.data.alert.DataAlertMapper
import ru.nbsp.pushka.mapper.data.source.DataCategoryMapper
import ru.nbsp.pushka.mapper.data.source.DataSourceMapper
import ru.nbsp.pushka.mapper.data.subscription.DataSubscriptionMapper
import ru.nbsp.pushka.network.service.PushkaAuthService
import ru.nbsp.pushka.network.service.PushkaDeviceService
import ru.nbsp.pushka.network.service.PushkaSubscriptionService
import ru.nbsp.pushka.util.DeviceUtils
import ru.nbsp.pushka.util.SchedulersUtils
import javax.inject.Singleton

/**
 * Created by Dimorinny on 21.02.16.
 */
@Singleton
@Module
class InteractorModule {

    @Singleton
    @Provides
    fun provideUserInteractor(api: PushkaAuthService, schedulersUtils: SchedulersUtils): UserInteractor {
        return ApiUserInteractor(api, schedulersUtils)
    }

    @Singleton
    @Provides
    fun provideApiSubscriptionInteractor(api: PushkaSubscriptionService, schedulersUtils: SchedulersUtils): ApiSubscriptionInteractor {
        return ApiSubscriptionInteractorImpl(api, schedulersUtils)
    }

    @Singleton
    @Provides
    fun provideStorageSubscriptionInteractor(dataManager: DataManager, dataSubscriptionMapper: DataSubscriptionMapper): StorageSubscriptionInteractor {
        return StorageSubscriptionInteractorImpl(dataManager, dataSubscriptionMapper)
    }

    @Singleton
    @Provides
    fun provideApiDeviceInteractor(api: PushkaDeviceService, deviceUtils: DeviceUtils, schedulersUtils: SchedulersUtils): ApiDeviceInteractor {
        return ApiDeviceInteractorImpl(api, deviceUtils, schedulersUtils)
    }

    @Singleton
    @Provides
    fun provideStorageSourceInteractor(dataManager: DataManager, dataSourceMapper: DataSourceMapper): StorageSourceInteractor {
        return StorageSourceInteractorImpl(dataManager, dataSourceMapper)
    }

    @Singleton
    @Provides
    fun provideStorageAlertInteractor(dataManager: DataManager, dataAlertMapper: DataAlertMapper): StorageAlertInteractor {
        return StorageAlertInteractorImpl(dataManager, dataAlertMapper)
    }

    @Singleton
    @Provides
    fun provideStorageCategoryInteractor(dataManager: DataManager, dataCategoryMapper: DataCategoryMapper): StorageCategoryInteractor {
        return StorageCategoryInteractorImpl(dataManager, dataCategoryMapper)
    }
}