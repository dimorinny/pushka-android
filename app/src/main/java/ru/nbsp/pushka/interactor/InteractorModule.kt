package ru.nbsp.pushka.interactor

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.gcm.manage.GcmManager
import ru.nbsp.pushka.interactor.alert.StorageAlertInteractor
import ru.nbsp.pushka.interactor.alert.StorageAlertInteractorImpl
import ru.nbsp.pushka.interactor.app.ApplicationInteractor
import ru.nbsp.pushka.interactor.app.ApplicationInteractorImpl
import ru.nbsp.pushka.interactor.category.StorageCategoryInteractor
import ru.nbsp.pushka.interactor.category.StorageCategoryInteractorImpl
import ru.nbsp.pushka.interactor.data.DataInteractor
import ru.nbsp.pushka.interactor.data.DataInteractorImpl
import ru.nbsp.pushka.interactor.device.ApiDeviceInteractor
import ru.nbsp.pushka.interactor.device.ApiDeviceInteractorImpl
import ru.nbsp.pushka.interactor.device.StorageDeviceInteractor
import ru.nbsp.pushka.interactor.device.StorageDeviceInteractorImpl
import ru.nbsp.pushka.interactor.source.StorageSourceInteractor
import ru.nbsp.pushka.interactor.source.StorageSourceInteractorImpl
import ru.nbsp.pushka.interactor.subscription.ApiSubscriptionInteractor
import ru.nbsp.pushka.interactor.subscription.ApiSubscriptionInteractorImpl
import ru.nbsp.pushka.interactor.subscription.StorageSubscriptionInteractor
import ru.nbsp.pushka.interactor.subscription.StorageSubscriptionInteractorImpl
import ru.nbsp.pushka.interactor.user.ApiUserInteractor
import ru.nbsp.pushka.interactor.user.UserInteractor
import ru.nbsp.pushka.mapper.data.alert.DataAlertMapper
import ru.nbsp.pushka.mapper.data.device.DataDeviceMapper
import ru.nbsp.pushka.mapper.data.source.DataCategoryMapper
import ru.nbsp.pushka.mapper.data.source.DataSourceMapper
import ru.nbsp.pushka.mapper.data.subscription.DataSubscriptionMapper
import ru.nbsp.pushka.mapper.presentation.subscription.PresentationSubscriptionMapper
import ru.nbsp.pushka.network.auth.AccountManager
import ru.nbsp.pushka.network.service.PushkaAuthService
import ru.nbsp.pushka.network.service.PushkaDeviceService
import ru.nbsp.pushka.network.service.PushkaSubscriptionService
import ru.nbsp.pushka.service.ServiceManager
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
    fun provideApiSubscriptionInteractor(api: PushkaSubscriptionService, presentationSubscriptionMapper: PresentationSubscriptionMapper, schedulersUtils: SchedulersUtils): ApiSubscriptionInteractor {
        return ApiSubscriptionInteractorImpl(api, presentationSubscriptionMapper, schedulersUtils)
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

    @Singleton
    @Provides
    fun provideStorageDeviceInteractor(dataManager: DataManager, dataDeviceMapper: DataDeviceMapper): StorageDeviceInteractor {
        return StorageDeviceInteractorImpl(dataManager, dataDeviceMapper)
    }

    @Singleton
    @Provides
    fun provideApplicationInteractor(context: Context, serviceManager: ServiceManager, accountMananager: AccountManager, gcmManager: GcmManager): ApplicationInteractor {
        return ApplicationInteractorImpl(context, serviceManager, accountMananager, gcmManager)
    }

    @Singleton
    @Provides
    fun provideDataInteractor(dataManager: DataManager): DataInteractor {
        return DataInteractorImpl(dataManager)
    }
}