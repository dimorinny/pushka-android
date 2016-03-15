package ru.nbsp.pushka.mapper.presentation.alert

import ru.nbsp.pushka.data.model.alert.DataAlert
import ru.nbsp.pushka.network.model.alert.NetworkAlert
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAction
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 06.03.16.
 */
@Singleton
class PresentationAlertMapper
    @Inject constructor(val presentationActionMapper: PresentationActionMapper) {

    fun fromDataAlert(dataAlert: DataAlert): PresentationAlert {
        val actions = ArrayList<PresentationAction>()

        for (action in dataAlert.actions) {
            actions.add(presentationActionMapper.fromDataAction(action))
        }

        return PresentationAlert(dataAlert.id, dataAlert.title, dataAlert.text,
                dataAlert.photo, dataAlert.sourceImage, dataAlert.sourceTitle, dataAlert.shareLink, actions)
    }

    fun fromNetworkAlert(networkAlert: NetworkAlert): PresentationAlert {
        val actions = ArrayList<PresentationAction>()

        for (action in networkAlert.notification.actions) {
            actions.add(presentationActionMapper.fromNetworkAction(action))
        }

        return PresentationAlert(networkAlert.id, networkAlert.notification.title, networkAlert.notification.description,
                networkAlert.notification.image, networkAlert.notification.icon, null, "null", actions)
    }
}