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

        return DataSource(
                id = presentationSource.id,
                params = params,
                name = presentationSource.name,
                description = presentationSource.description,
                category = presentationSource.category,
                color = presentationSource.color,
                icon = presentationSource.icon)
    }

    fun fromNetworkSource(networkSource: NetworkSource): DataSource {
        var params = RealmList<DataParam>()

        for (param in networkSource.params) {
            params.add(paramMapper.fromNetworkParam(param))
        }

        return DataSource(
                id = networkSource.id,
                params = params,
                name = networkSource.name,
                description = networkSource.description,
                icon = networkSource.icon,
                color = networkSource.color,
                category = networkSource.category)
    }
}