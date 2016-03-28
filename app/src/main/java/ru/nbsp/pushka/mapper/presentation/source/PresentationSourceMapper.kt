package ru.nbsp.pushka.mapper.presentation.source

import ru.nbsp.pushka.data.model.source.DataSource
import ru.nbsp.pushka.network.model.source.NetworkSource
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 06.03.16.
 */
@Singleton
class PresentationSourceMapper
    @Inject constructor(val paramMapper: PresentationParamMapper) {

    fun fromDataSource(dataSource: DataSource): PresentationSource {
        var params = ArrayList<PresentationParam>()

        for (param in dataSource.params) {
            params.add(paramMapper.fromDataParam(param))
        }

        return PresentationSource(
                id = dataSource.id,
                params = params,
                name = dataSource.name,
                description = dataSource.description,
                category = dataSource.category,
                icon = dataSource.icon,
                color = dataSource.color)
    }

    fun fromNetworkSource(networkSource: NetworkSource): PresentationSource {
        var params = ArrayList<PresentationParam>()

        for (param in networkSource.params) {
            params.add(paramMapper.fromNetworkParam(param))
        }

        return PresentationSource(
                id = networkSource.id,
                params = params,
                name = networkSource.name,
                description = networkSource.description,
                category = networkSource.category,
                icon = networkSource.icon,
                color = networkSource.color)
    }
}