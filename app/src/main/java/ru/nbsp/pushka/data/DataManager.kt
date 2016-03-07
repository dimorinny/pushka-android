package ru.nbsp.pushka.data

import io.realm.Realm
import io.realm.RealmObject
import ru.nbsp.pushka.data.model.alert.DataAlert
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
        return getListObservable(DataAlert::class.java)
    }

    fun clearAlerts() {
        realmProvider.get().executeTransaction {
            it.clear(DataAlert::class.java)
        }
    }

    fun putAlerts(alerts: List<DataAlert>) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(alerts)
        }
    }

    fun getSourcesObservable(): Observable<List<DataSource>> {
        return getListObservable(DataSource::class.java)
    }

    fun clearSources() {
        realmProvider.get().executeTransaction {
            it.clear(DataSource::class.java)
        }
    }

    fun putSources(sources: List<DataSource>) {
        realmProvider.get().executeTransaction {
            it.copyToRealm(sources)
        }
    }

    private fun <T : RealmObject> getListObservable(type: Class<T>): Observable<List<T>> {
        return realmProvider.get().where(type)
                .findAllAsync()
                .asObservable()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }
    }
}