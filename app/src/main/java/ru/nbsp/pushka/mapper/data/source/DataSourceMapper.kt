package ru.nbsp.pushka.mapper.data.source

import io.realm.RealmList
import ru.nbsp.pushka.data.model.source.DataParam
import ru.nbsp.pushka.data.model.source.DataSource
import ru.nbsp.pushka.network.model.source.NetworkSource
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 13.03.16.
 */
@Singleton
class DataSourceMapper
    @Inject constructor(val paramMapper: DataParamMapper) {

    fun fromPresentationSource(presentationSource: PresentationSource): DataSource {
        var params = RealmList<DataParam>()

        for (param in presentationSource.params) {
            params.add(paramMapper.fromPresentationParam(param))
        }

        return DataSource(presentationSource.id, params, presentationSource.name, presentationSource.description, presentationSource.category)
    }

    fun fromNetworkSource(networkSource: NetworkSource): DataSource {
        var params = RealmList<DataParam>()

        for (param in networkSource.params) {
            params.add(paramMapper.fromNetworkParam(param))
        }

        return DataSource(networkSource.id, params, networkSource.name, networkSource.description, networkSource.category)
    }
}