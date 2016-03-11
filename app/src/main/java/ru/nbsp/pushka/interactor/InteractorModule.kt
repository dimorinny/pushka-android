package ru.nbsp.pushka.interactor

import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.interactor.alert.StorageAlertInteractor
import ru.nbsp.pushka.interactor.alert.StorageAlertInteractorImpl
import ru.nbsp.pushka.interactor.category.StorageCategoryInteractor
import ru.nbsp.pushka.interactor.category.StorageCategoryInteractorImpl
import ru.nbsp.pushka.interactor.source.ApiSourceInteractor
import ru.nbsp.pushka.interactor.source.ApiSourceInteractorImpl
import ru.nbsp.pushka.interactor.source.StorageSourceInteractor
import ru.nbsp.pushka.interactor.source.StorageSourceInteractorImpl
import ru.nbsp.pushka.interactor.user.ApiUserInteractor
import ru.nbsp.pushka.interactor.user.UserInteractor
import ru.nbsp.pushka.mapper.data.alert.DataAlertMapper
import ru.nbsp.pushka.mapper.data.source.DataCategoryMapper
import ru.nbsp.pushka.network.service.PushkaAuthService
import ru.nbsp.pushka.network.service.PushkaSourceService
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
    fun provideApiSourceInteractor(api: PushkaSourceService, schedulersUtils: SchedulersUtils): ApiSourceInteractor {
        return ApiSourceInteractorImpl(api, schedulersUtils)
    }

    @Singleton
    @Provides
    fun provideStorageSourceInteractor(dataManager: DataManager): StorageSourceInteractor {
        return StorageSourceInteractorImpl(dataManager)
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