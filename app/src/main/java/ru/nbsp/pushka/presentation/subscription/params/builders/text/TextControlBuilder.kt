package ru.nbsp.pushka.presentation.subscription.params.builders.text

import android.content.Context
import com.google.gson.Gson
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import ru.nbsp.pushka.presentation.subscription.params.builders.ControlBuilder
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.presentation.subscription.params.control.text.TextControl

/**
 * Created by Dimorinny on 25.04.16.
 */
class TextControlBuilder(val gson: Gson) : ControlBuilder.Builder {

    override fun build(control: PresentationControl, context: Context): Control {
        val dropdown = TextControl(context)
        dropdown.setTitle(control.title)
        return dropdown
    }
}