package ru.nbsp.pushka.mapper.data.alert

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
    @Inject constructor() {

    fun fromPresentationAlert(presentationAlert: PresentationAlert): DataAlert {
        return DataAlert(presentationAlert.id, presentationAlert.title, presentationAlert.text,
                presentationAlert.photo, presentationAlert.sourceImage,
                presentationAlert.sourceTitle, presentationAlert.shareLink)
    }

    fun fromNetworkAlert(networkAlert: NetworkAlert): DataAlert {
        return DataAlert(networkAlert.id, networkAlert.notification.title, networkAlert.notification.description,
                networkAlert.notification.image, networkAlert.notification.icon, null, "null")
    }
}