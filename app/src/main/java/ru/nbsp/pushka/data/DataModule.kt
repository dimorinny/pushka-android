package ru.nbsp.pushka.data

import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

/**
 * Created by Dimorinny on 04.01.16.
 */
@Singleton
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideRealm(realmConfiguration: RealmConfiguration): Realm {
        return Realm.getInstance(realmConfiguration)
    }

    @Singleton
    @Provides
    fun provideRealmConfiguration(context: Context): RealmConfiguration {
        return RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build()
    }
}