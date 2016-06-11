package ru.nbsp.pushka.presentation.subscription.params.builders.number

import android.content.Context
import com.google.gson.Gson
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import ru.nbsp.pushka.presentation.core.model.subscription.control.NumberAttributes
import ru.nbsp.pushka.presentation.subscription.params.builders.ControlBuilder
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.presentation.subscription.params.control.number.NumberControl

/**
 * Created by Dimorinny on 11.04.16.
 */
class NumberControlBuilder(val gson: Gson) : ControlBuilder.Builder {

    override fun build(control: PresentationControl, context: Context): Control {
        val attributes = gson.fromJson(control.attributes, NumberAttributes::class.java)
        val number = NumberControl(context, attributes)
        number.setTitle(control.title)
        return number
    }
}