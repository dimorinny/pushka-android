package ru.nbsp.pushka.iteractor

import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.api.PushkaInterface
import ru.nbsp.pushka.iteractor.user.ApiUserIteractor
import ru.nbsp.pushka.iteractor.user.UserIteractor
import ru.nbsp.pushka.util.SchedulersManager
import javax.inject.Singleton

/**
 * Created by Dimorinny on 21.02.16.
 */
@Singleton
@Module
class IteractorModule {

    @Singleton
    @Provides
    fun provideUserIteractor(api: PushkaInterface, schedulersManager: SchedulersManager): UserIteractor {
        return ApiUserIteractor(api, schedulersManager)
    }
}