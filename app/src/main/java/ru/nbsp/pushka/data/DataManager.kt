package ru.nbsp.pushka.data

import io.realm.Realm
import io.realm.Sort
import ru.nbsp.pushka.data.model.alert.DataAlert
import ru.nbsp.pushka.data.model.source.DataCategory
import ru.nbsp.pushka.data.model.source.DataSource
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

    fun getAlertsObservable(): Observable<List<DataAlert>> {
        return realmProvider.get()
                .where(DataAlert::class.java)
                .findAllSorted("date", Sort.DESCENDING)
                .asObservable()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }
    }

    fun clearAlerts() {
        realmProvider.get().executeTransaction {
            // TODO: Cascade deletes not worked in realm now. Look this issue https://github.com/realm/realm-java/issues/1104
            val alerts = it.where(DataAlert::class.java).findAll()

            for (i in 0..alerts.size - 1) {
                val alert = alerts[i]

                for (j in 0..alert.actions.size - 1) {
                    val action = alert.actions[j]
                    action.removeFromRealm()
                }
            }

            alerts.clear()
        }
    }

    fun putAlerts(alerts: List<DataAlert>) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(alerts)
        }
    }

    fun getAlertObservable(alertId: String): Observable<DataAlert> {
        return realmProvider.get().where(DataAlert::class.java)
                .equalTo("id", alertId)
                .findFirst()
                .asObservable()
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

            for (i in 0..alert.actions.size - 1) {
                val action = alert.actions[i]
                action.removeFromRealm()
            }

            alert.removeFromRealm()
        }
    }

    fun getSourcesObservable(categoryId: String): Observable<List<DataSource>> {
        return realmProvider.get().where(DataSource::class.java)
                .equalTo("category", categoryId)
                .findAll()
                .asObservable()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }
    }

    fun clearSources() {
        realmProvider.get().executeTransaction {
            it.clear(DataSource::class.java)
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
                    param.control.removeFromRealm()
                    param.removeFromRealm()
                }
            }

            sources.clear()
        }
    }

    fun putSources(sources: List<DataSource>) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(sources)
        }
    }

    fun putCategories(categories: List<DataCategory>) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(categories)
        }
    }

    fun clearCategories() {
        realmProvider.get().executeTransaction {
            it.clear(DataCategory::class.java)
        }
    }

    fun getCategoriesObservable(): Observable<List<DataCategory>> {
        return realmProvider.get().where(DataCategory::class.java)
                .findAll()
                .asObservable()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }
    }
}