package ru.nbsp.pushka.service.api

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.di.annotation.ApiRepository
import ru.nbsp.pushka.bus.RxBus
import ru.nbsp.pushka.bus.event.BaseEvent
import ru.nbsp.pushka.bus.event.source.LoadCategoriesEvent
import ru.nbsp.pushka.bus.event.source.LoadSourceEvent
import ru.nbsp.pushka.bus.event.source.LoadSourcesEvent
import ru.nbsp.pushka.interactor.category.StorageCategoryInteractor
import ru.nbsp.pushka.interactor.source.StorageSourceInteractor
import ru.nbsp.pushka.repository.category.CategoriesRepository
import ru.nbsp.pushka.repository.source.SourcesRepository
import ru.nbsp.pushka.service.BaseEventSubscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by Dimorinny on 31.03.16.
 */
class ApiSourceService : Service() {

    @Inject
    lateinit var bus: RxBus

    @field:[Inject ApiRepository]
    lateinit var apiSourcesRepository: SourcesRepository

    @Inject
    lateinit var storageSourcesInteractor: StorageSourceInteractor

    @field:[Inject ApiRepository]
    lateinit var apiCategoriesRepository: CategoriesRepository

    @Inject
    lateinit var storageCategoryInteractor: StorageCategoryInteractor

    private val subscription = CompositeSubscription()

    companion object {
        const val ARG_SERVICE_COMMAND = "arg_service_command"
        const val ARG_CATEGORY_ID = "arg_category_id"
        const val ARG_SOURCE_ID = "arg_source_id"

        const val COMMAND_LOAD_SOURCES = "command_load_sources"
        const val COMMAND_LOAD_CATEGORIES = "command_load_categories"
        const val COMMAND_LOAD_SOURCE = "command_load_source"
    }

    override fun onBind(intent: Intent?): IBinder? { return null }

    override fun onCreate() {
        super.onCreate()
        BaseApplication.graph.inject(this)
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            return START_NOT_STICKY
        }

        val command = intent.getStringExtra(ARG_SERVICE_COMMAND)

        when (command) {
            COMMAND_LOAD_SOURCES -> {
                handleLoadSourcesCommand(intent, startId)
            }
            COMMAND_LOAD_CATEGORIES -> {
                handleLoadCategoriesCommand(startId)
            }
            COMMAND_LOAD_SOURCE -> {
                handleLoadSourceCommand(intent, startId)
            }
        }

        return START_NOT_STICKY
    }

    private fun handleLoadSourceCommand(intent: Intent, startId: Int) {
        val sourceId = intent.getStringExtra(ARG_SOURCE_ID)

        apiSourcesRepository.getSource(sourceId)
                .flatMap {
                    storageSourcesInteractor.saveSource(it)
                }
                .subscribe(object : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return LoadSourceEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return LoadSourceEvent.Success(sourceId)
                    }
                })
    }

    private fun handleLoadCategoriesCommand(startId: Int) {
        apiCategoriesRepository.getCategories()
                .flatMap {
                    storageCategoryInteractor.saveCategories(it)
                }
                .subscribe(object : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return LoadCategoriesEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return LoadCategoriesEvent.Success()
                    }
                })
    }

    private fun handleLoadSourcesCommand(intent: Intent, startId: Int) {
        val categoryId = intent.getStringExtra(ARG_CATEGORY_ID)

        apiSourcesRepository.getSources(categoryId)
                .flatMap {
                    storageSourcesInteractor.saveSources(it, categoryId)
                }
                .subscribe(object : BaseEventSubscriber(this, startId, bus) {
                    override fun error(t: Throwable): BaseEvent {
                        return LoadSourcesEvent.Error(t)
                    }

                    override fun success(): BaseEvent {
                        return LoadSourcesEvent.Success()
                    }
                })
    }
}