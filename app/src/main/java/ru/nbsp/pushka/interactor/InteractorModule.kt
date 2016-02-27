package ru.nbsp.pushka.interactor

import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.interactor.source.ApiSourceInteractor
import ru.nbsp.pushka.interactor.source.SourceInteractor
import ru.nbsp.pushka.interactor.user.ApiUserInteractor
import ru.nbsp.pushka.interactor.user.UserInteractor
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
}