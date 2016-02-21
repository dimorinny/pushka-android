package ru.nbsp.pushka.iteractor

import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.api.PushkaInterface
import ru.nbsp.pushka.iteractor.user.ApiUserIteractor
import ru.nbsp.pushka.iteractor.user.UserIteractor
import ru.nbsp.pushka.util.SchedulersUtils
import javax.inject.Singleton

/**
 * Created by Dimorinny on 21.02.16.
 */
@Singleton
@Module
class IteractorModule {

    @Singleton
    @Provides
    fun provideUserIteractor(api: PushkaInterface, schedulersUtils: SchedulersUtils): UserIteractor {
        return ApiUserIteractor(api, schedulersUtils)
    }
}