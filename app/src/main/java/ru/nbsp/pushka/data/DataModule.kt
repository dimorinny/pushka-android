package ru.nbsp.pushka.data

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping
import com.pushtorefresh.storio.sqlite.StorIOSQLite
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite
import dagger.Module
import dagger.Provides
import ru.nbsp.pushka.data.entity.Alert
import ru.nbsp.pushka.data.entity.AlertStorIOSQLiteDeleteResolver
import ru.nbsp.pushka.data.entity.AlertStorIOSQLiteGetResolver
import ru.nbsp.pushka.data.entity.AlertStorIOSQLitePutResolver
import javax.inject.Singleton

/**
 * Created by Dimorinny on 04.01.16.
 */
@Singleton
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideStoreIOSQLite(sqLiteOpenHelper: SQLiteOpenHelper): StorIOSQLite {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(Alert::class.java, SQLiteTypeMapping.builder<Alert>()
                        .putResolver(AlertStorIOSQLitePutResolver())
                        .getResolver(AlertStorIOSQLiteGetResolver())
                        .deleteResolver(AlertStorIOSQLiteDeleteResolver())
                        .build())
                .build();
    }

    @Singleton
    @Provides
    fun provideSQLiteOpenHelper(context: Context): SQLiteOpenHelper {
        return DbOpenHelper(context)
    }
}