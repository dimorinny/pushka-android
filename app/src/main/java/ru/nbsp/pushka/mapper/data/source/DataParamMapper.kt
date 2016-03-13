package ru.nbsp.pushka.mapper.data.source

import ru.nbsp.pushka.data.model.source.DataParam
import ru.nbsp.pushka.network.model.source.NetworkParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 06.03.16.
 */
@Singleton
class DataParamMapper
    @Inject constructor(val controlMapper: DataControlMapper) {

    fun fromPresentationParam(presentationParam: PresentationParam): DataParam {
        return DataParam(presentationParam.name, presentationParam.required, controlMapper.fromPresentationControl(presentationParam.control))
    }

    fun fromNetworkParam(networkParam: NetworkParam): DataParam {
        return DataParam(networkParam.name, networkParam.required, controlMapper.fromNetworkControl(networkParam.control))
    }
}