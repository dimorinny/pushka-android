package ru.nbsp.pushka.di

import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.BaseApplication
import javax.inject.Singleton

/**
 * Created by Dimorinny on 11.02.16.
 */

@Singleton
@Module
class AppModule(val application: BaseApplication) {

    @Singleton
    @Provides
    fun provideContext(): BaseApplication {
        return application
    }
}