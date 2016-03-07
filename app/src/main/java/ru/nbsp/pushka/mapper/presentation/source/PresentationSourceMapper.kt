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

        return PresentationSource(dataSource.id, params, dataSource.name, dataSource.description, dataSource.category)
    }

    fun fromNetworkSource(networkSource: NetworkSource): PresentationSource {
        var params = ArrayList<PresentationParam>()

        for (param in networkSource.params) {
            params.add(paramMapper.fromNetworkParam(param))
        }

        return PresentationSource(networkSource.id, params, networkSource.name, networkSource.description, networkSource.category)
    }
}