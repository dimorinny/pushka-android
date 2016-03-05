package ru.nbsp.pushka.data

import com.pushtorefresh.storio.sqlite.StorIOSQLite
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery
import com.pushtorefresh.storio.sqlite.queries.Query
import ru.nbsp.pushka.data.model.alert.Alert
import ru.nbsp.pushka.data.table.AlertTable
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 02.03.16.
 */
@Singleton
class DataManager @Inject constructor(val storIO: StorIOSQLite) {

    fun getAletsObservable() : Observable<List<Alert>> {
        return storIO.get()
                .listOfObjects(Alert::class.java)
                .withQuery(Query.builder()
                        .table(AlertTable.TABLE)
                        .build())
                .prepare()
                .createObservable()
    }

    fun clearAlerts() {
        storIO.delete()
                .byQuery(DeleteQuery.builder().table(AlertTable.TABLE).build())
                .prepare()
                .executeAsBlocking()
    }

    fun putAlerts(servers: List<Alert>) {
        storIO.put()
                .objects(servers)
                .prepare()
                .executeAsBlocking()
    }
}