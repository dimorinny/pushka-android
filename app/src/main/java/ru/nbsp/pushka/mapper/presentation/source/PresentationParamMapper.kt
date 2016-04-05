package ru.nbsp.pushka.mapper.presentation.source

import ru.nbsp.pushka.data.model.source.DataParam
import ru.nbsp.pushka.network.model.source.NetworkParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 06.03.16.
 */
@Singleton
class PresentationParamMapper
    @Inject constructor(val controlMapper: PresentationControlMapper) {

    fun fromDataParam(dataParam: DataParam): PresentationParam {
        return PresentationParam(
                name = dataParam.name,
                required = dataParam.required,
                control = controlMapper.fromDataControl(dataParam.control))
    }

    fun fromNetworkParam(networkPresentation: NetworkParam): PresentationParam {
        return PresentationParam(
                name = networkPresentation.name,
                required = networkPresentation.required,
                control = controlMapper.fromNetworkControl(networkPresentation.control))
    }
}