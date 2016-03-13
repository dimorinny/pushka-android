package ru.nbsp.pushka.data

import io.realm.Realm
import io.realm.RealmObject
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

            for (source in sources) {
                for (param in source.params) {
                    param.control.removeFromRealm()
                }
                source.params.clear()
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
        return getListObservable(DataCategory::class.java)
    }

    private fun <T : RealmObject> getListObservable(type: Class<T>): Observable<List<T>> {
        return realmProvider.get().where(type)
                .findAll()
                .asObservable()
                .map {
                    realmProvider.get().copyFromRealm(it)
                }
    }
}