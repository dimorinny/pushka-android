package ru.nbsp.pushka.mapper.data.source

import ru.nbsp.pushka.data.model.source.DataControl
import ru.nbsp.pushka.network.model.source.NetworkControl
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 06.03.16.
 */
@Singleton
class DataControlMapper @Inject constructor() {

    fun fromPresentationControl(presentationControl: PresentationControl): DataControl {
        return DataControl(
                type = presentationControl.type,
                title = presentationControl.title,
                options = presentationControl.attributes.toString())
    }

    fun fromNetworkControl(networkControl: NetworkControl): DataControl {
        return DataControl(
                type = networkControl.type,
                title = networkControl.title,
                options = networkControl.attributes.toString())
    }
}