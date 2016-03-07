package ru.nbsp.pushka.interactor

import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.data.DataManager
import ru.nbsp.pushka.interactor.alert.AlertInteractor
import ru.nbsp.pushka.interactor.alert.StorageAlertInteractor
import ru.nbsp.pushka.interactor.source.ApiSourceInteractor
import ru.nbsp.pushka.interactor.source.SourceInteractor
import ru.nbsp.pushka.interactor.user.ApiUserInteractor
import ru.nbsp.pushka.interactor.user.UserInteractor
import ru.nbsp.pushka.mapper.data.alert.DataAlertMapper
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
    fun provideUserIteractor(api: PushkaAuthService, schedulersUtils: SchedulersUtils): UserInteractor {
        return ApiUserInteractor(api, schedulersUtils)
    }

    @Singleton
    @Provides
    fun provideSourceInteractor(api: PushkaSourceService, schedulersUtils: SchedulersUtils): SourceInteractor {
        return ApiSourceInteractor(api, schedulersUtils)
    }

    @Singleton
    @Provides
    fun provideAlertInteractor(dataManager: DataManager, dataAlertMapper: DataAlertMapper, schedulersUtils: SchedulersUtils): AlertInteractor {
        return StorageAlertInteractor(dataManager, dataAlertMapper, schedulersUtils)
    }
}