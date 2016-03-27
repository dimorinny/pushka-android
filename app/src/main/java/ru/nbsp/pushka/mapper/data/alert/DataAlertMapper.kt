package ru.nbsp.pushka.mapper.data.alert

import io.realm.RealmList
import ru.nbsp.pushka.data.model.alert.DataAction
import ru.nbsp.pushka.data.model.alert.DataAlert
import ru.nbsp.pushka.network.model.alert.NetworkAlert
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 07.03.16.
 */
@Singleton
class DataAlertMapper
    @Inject constructor(val dataActionMapper: DataActionMapper) {

    fun fromPresentationAlert(presentationAlert: PresentationAlert): DataAlert {
        val actions = RealmList<DataAction>()

        for (action in presentationAlert.actions) {
            actions.add(dataActionMapper.fromPresentationAction(action))
        }

        return DataAlert(id = presentationAlert.id,
                title = presentationAlert.title,
                text = presentationAlert.text,
                photo = presentationAlert.photo,
                date = presentationAlert.date,
                sourceImage = presentationAlert.sourceImage,
                sourceTitle = presentationAlert.sourceTitle,
                shareLink = presentationAlert.shareLink,
                color = presentationAlert.color,
                actions = actions)
    }

    fun fromNetworkAlert(networkAlert: NetworkAlert): DataAlert {
        val actions = RealmList<DataAction>()

        for (action in networkAlert.notification.actions) {
            actions.add(dataActionMapper.fromNetworkAction(action))
        }

        return DataAlert(id = networkAlert.id,
                title = networkAlert.notification.title,
                text = networkAlert.notification.description,
                photo = networkAlert.notification.image,
                sourceImage = networkAlert.notification.icon,
                color = networkAlert.notification.color,
                sourceTitle = networkAlert.notification.sourceTitle,
                shareLink = "null",
                actions = actions)
    }
}