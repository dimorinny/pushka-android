package ru.nbsp.pushka.di

import dagger.Module
import dagger.Provides
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Dimorinny on 17.02.16.
 */

@Singleton
@Module
class SchedulerModule {
    companion object {
        const val IO = "IO"
        const val UI = "UI"
    }

    @Singleton
    @Provides
    @Named(IO)
    fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Singleton
    @Provides
    @Named(UI)
    fun provideUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}