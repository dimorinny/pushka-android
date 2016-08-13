package ru.nbsp.pushka.data

import io.realm.Realm
import io.realm.Sort
import ru.nbsp.pushka.data.model.alert.DataAlert
import ru.nbsp.pushka.data.model.device.DataDevice
import ru.nbsp.pushka.data.model.source.DataCategory
import ru.nbsp.pushka.data.model.source.DataSource
import ru.nbsp.pushka.data.model.subscription.DataSubscription
import rx.Observable
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Dimorinny on 02.03.16.
 */
@Singleton
class DataManager
    @Inject constructor(val realmProvider: Provider<Realm>) {

    fun getAlertsObservable(): Observable<List<DataAlert>> =
            realmProvider.get().where(DataAlert::class.java)
                .findAllSorted("date", Sort.DESCENDING)
                .asObservable()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }

    fun clearAlerts() {
        realmProvider.get().executeTransaction {
            // TODO: Cascade deletes not worked in realm now. Look this issue https://github.com/realm/realm-java/issues/1104
            val alerts = it.where(DataAlert::class.java).findAll()

            for (i in 0..alerts.size - 1) {
                val alert = alerts[i]

                for (j in 0..alert.actions.size - 1) {
                    val action = alert.actions[j]
                    action.deleteFromRealm()
                }
            }

            alerts.deleteAllFromRealm()
        }
    }

    fun putAlerts(alerts: List<DataAlert>) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(alerts)
        }
    }

//    fun addAlerts(alerts: List<DataAlert>) {
//        realmProvider.get().executeTransaction {
//            it.add
//        }
//    }

    fun getAlertObservable(alertId: String): Observable<DataAlert> =
            realmProvider.get().where(DataAlert::class.java)
                .equalTo("id", alertId)
                .findFirst()
                .run {
                    if (this != null) {
                        asObservable()
                    } else {
                        Observable.empty()
                    }
                }

    // I don't use realm filter, because it does not support normal filter for cyrillic symbols.
    // For more information look https://realm.io/docs/java/latest/#current-limitations
    fun getAlertsWithFilter(query: String): Observable<List<DataAlert>> =
            realmProvider.get().where(DataAlert::class.java)
                .findAllSorted("date", Sort.DESCENDING)
                .asObservable()
                .first()
                .flatMapIterable { it }
                .filter { it.title.contains(query, true) || it.sourceTitle.contains(query, true) }
                .toList()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }

    fun putAlert(alert: DataAlert) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(alert)
        }
    }

    fun clearAlert(alertId: String) {
        realmProvider.get().executeTransaction {
            val alert = it.where(DataAlert::class.java)
                    .equalTo("id", alertId)
                    .findFirst()

            if (alert != null) {
                for (i in 0..alert.actions.size - 1) {
                    val action = alert.actions[i]
                    action.deleteFromRealm()
                }
                alert.deleteFromRealm()
            }
        }
    }

    fun getSourcesObservable(categoryId: String): Observable<List<DataSource>> =
            realmProvider.get().where(DataSource::class.java)
                .equalTo("category", categoryId)
                .findAllSorted("name")
                .asObservable()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }

    fun clearSources() {
        realmProvider.get().executeTransaction {
            it.delete(DataSource::class.java)
        }
    }

    fun clearSources(categoryId: String) {
        // TODO: Cascade deletes not worked in realm now. Look this issue https://github.com/realm/realm-java/issues/1104
        realmProvider.get().executeTransaction {

            val sources = it.where(DataSource::class.java)
                    .equalTo("category", categoryId)
                    .findAll()

            for (i in 0..sources.size - 1) {
                val source = sources[i]

                for (j in 0..source.params.size - 1) {
                    val param = source.params[j]
                    param.control.deleteFromRealm()
                    param.deleteFromRealm()
                }
            }

            sources.deleteAllFromRealm()
        }
    }

    fun putSources(sources: List<DataSource>) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(sources)
        }
    }

    fun putSource(source: DataSource) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(source)
        }
    }

    fun clearSource(sourceId: String) {
        realmProvider.get().executeTransaction {
            val subscription = it.where(DataSource::class.java)
                    .equalTo("id", sourceId)
                    .findFirst()

            subscription?.deleteFromRealm()
        }
    }

    fun getSourceObservable(sourceId: String): Observable<DataSource> =
            realmProvider.get().where(DataSource::class.java)
                .equalTo("id", sourceId)
                .findFirst()
                .run {
                    if (this != null) {
                        asObservable()
                    } else {
                        Observable.empty()
                    }
                }

    fun putCategories(categories: List<DataCategory>) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(categories)
        }
    }

    fun clearCategories() {
        realmProvider.get().executeTransaction {
            it.delete(DataCategory::class.java)
        }
    }

    fun getCategoriesObservable(): Observable<List<DataCategory>> =
            realmProvider.get().where(DataCategory::class.java)
                .findAll()
                .asObservable()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }

    fun putSubscription(subscription: DataSubscription) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(subscription)
        }
    }

    fun getSubscriptionObservable(subscriptionId: String): Observable<DataSubscription> =
            realmProvider.get().where(DataSubscription::class.java)
                .equalTo("id", subscriptionId)
                .findFirst()
                .run {
                    if (this != null) {
                        asObservable()
                    } else {
                        Observable.empty()
                    }
                }

    fun clearSubscription(subscriptionId: String) {
        realmProvider.get().executeTransaction {
            val subscription = it.where(DataSubscription::class.java)
                    .equalTo("id", subscriptionId)
                    .findFirst()

            subscription?.deleteFromRealm()
        }
    }

    fun clearSubscriptions() {
        realmProvider.get().executeTransaction {
            it.delete(DataSubscription::class.java)
        }
    }

    fun putSubscriptions(subscriptions: List<DataSubscription>) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(subscriptions)
        }
    }

    fun getSubscriptionsObservable(): Observable<List<DataSubscription>> =
            realmProvider.get().where(DataSubscription::class.java)
                .findAllSorted("title")
                .asObservable()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }

    // I don't use realm filter, because it does not support normal filter for cyrillic symbols.
    // For more information look https://realm.io/docs/java/latest/#current-limitations
    fun getSubscriptionsWithFilter(query: String): Observable<List<DataSubscription>> =
            realmProvider.get().where(DataSubscription::class.java)
                .findAllSorted("title")
                .asObservable()
                .first()
                .flatMapIterable { it }
                .filter { it.title.contains(query, true) || it.sourceTitle.contains(query, true) }
                .toList()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }

    fun clearDevices() {
        realmProvider.get().executeTransaction {
            it.delete(DataDevice::class.java)
        }
    }

    fun clearDevice(deviceId: String) {
        realmProvider.get().executeTransaction {
            val subscription = it.where(DataDevice::class.java)
                    .equalTo("id", deviceId)
                    .findFirst()

            subscription?.deleteFromRealm()
        }
    }

    fun putDevices(devices: List<DataDevice>) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(devices)
        }
    }

    fun getDevicesObservable(): Observable<List<DataDevice>> =
            realmProvider.get().where(DataDevice::class.java)
                .findAll()
                .asObservable()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }

    fun clearAll() {
        clearAlerts()
        clearCategories()
        clearDevices()
        clearSources()
        clearSubscriptions()
    }
}