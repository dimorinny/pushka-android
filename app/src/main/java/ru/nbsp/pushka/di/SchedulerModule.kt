package ru.nbsp.pushka.di

import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.annotation.IOSched
import ru.nbsp.pushka.annotation.UISched
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Created by Dimorinny on 17.02.16.
 */

@Singleton
@Module
class SchedulerModule {

    @Singleton
    @Provides
    @IOSched
    fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Singleton
    @Provides
    @UISched
    fun provideUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}