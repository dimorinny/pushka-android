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

        return PresentationAlert(id = dataAlert.id,
                title = dataAlert.title,
                text = dataAlert.text,
                photo = dataAlert.photo,
                sourceImage = dataAlert.sourceImage,
                sourceTitle = dataAlert.sourceTitle,
                shareLink = dataAlert.shareLink,
                date = dataAlert.date,
                actions = actions)
    }

    fun fromNetworkAlert(networkAlert: NetworkAlert): PresentationAlert {
        val actions = ArrayList<PresentationAction>()

        for (action in networkAlert.notification.actions) {
            actions.add(presentationActionMapper.fromNetworkAction(action))
        }

        return PresentationAlert(id = networkAlert.id,
                title = networkAlert.notification.title,
                text = networkAlert.notification.description,
                photo = networkAlert.notification.image,
                date = networkAlert.notification.date,
                sourceImage = networkAlert.notification.icon,
                sourceTitle = null,
                shareLink = "null",
                actions = actions)
    }
}