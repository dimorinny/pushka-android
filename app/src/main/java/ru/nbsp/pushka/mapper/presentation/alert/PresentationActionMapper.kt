package ru.nbsp.pushka.mapper.presentation.alert

import ru.nbsp.pushka.data.model.alert.DataAction
import ru.nbsp.pushka.network.model.alert.NetworkAction
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAction
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 15.03.16.
 */
@Singleton
class PresentationActionMapper @Inject constructor() {
    fun fromDataAction(dataAction: DataAction): PresentationAction {
        return PresentationAction(
                type = dataAction.type,
                value = dataAction.value)
    }

    fun fromNetworkAction(networkAction: NetworkAction): PresentationAction {
        return PresentationAction(
                type = networkAction.type,
                value = networkAction.value)
    }
}