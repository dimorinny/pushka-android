package ru.nbsp.pushka.mapper.presentation.alert

import ru.nbsp.pushka.data.model.alert.DataAlert
import ru.nbsp.pushka.network.model.alert.NetworkAlert
import ru.nbsp.pushka.presentation.core.model.alert.PresentationAlert
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 06.03.16.
 */
@Singleton
class PresentationAlertMapper @Inject constructor() {
    fun fromDataAlert(dataAlert: DataAlert): PresentationAlert {
        return PresentationAlert(dataAlert.id, dataAlert.title, dataAlert.text,
                dataAlert.photo, dataAlert.sourceImage, dataAlert.sourceTitle, dataAlert.shareLink)
    }

    fun fromNetworkAlert(networkAlert: NetworkAlert): PresentationAlert {
        return PresentationAlert(networkAlert.id, networkAlert.title, networkAlert.text,
                networkAlert.photo, networkAlert.sourceImage, networkAlert.sourceTitle, networkAlert.shareLink)
    }
}