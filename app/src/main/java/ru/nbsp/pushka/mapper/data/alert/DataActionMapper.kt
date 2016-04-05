package ru.nbsp.pushka.mapper.data.alert

import ru.nbsp.pushka.data.model.alert.DataAction
import ru.nbsp.pushka.network.model.alert.NetworkAction
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAction
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 15.03.16.
 */
@Singleton
class DataActionMapper @Inject constructor() {
    fun fromPresentationAction(presentationAction: PresentationAction): DataAction {
        return DataAction(
                type = presentationAction.type,
                value = presentationAction.value)
    }

    fun fromNetworkAction(networkAction: NetworkAction): DataAction {
        return DataAction(
                type = networkAction.type,
                value = networkAction.value)
    }
}