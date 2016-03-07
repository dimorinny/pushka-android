package ru.nbsp.pushka.mapper.presentation.source

import com.google.gson.JsonParser
import ru.nbsp.pushka.data.model.source.DataControl
import ru.nbsp.pushka.network.model.source.NetworkControl
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 06.03.16.
 */
@Singleton
class PresentationControlMapper @Inject constructor() {
    fun fromDataControl(dataControl: DataControl): PresentationControl {
        return PresentationControl(dataControl.type, dataControl.title, JsonParser().parse(dataControl.options).asJsonObject)
    }

    fun fromNetworkControl(networkControl: NetworkControl): PresentationControl {
        return PresentationControl(networkControl.type, networkControl.title, networkControl.options)
    }
}