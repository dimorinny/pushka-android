package ru.nbsp.pushka.presentation.subscription.params.builders.autocomplete

import android.content.Context
import com.google.gson.Gson
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import ru.nbsp.pushka.presentation.core.model.subscription.control.ListAttributes
import ru.nbsp.pushka.presentation.subscription.params.builders.ControlBuilder
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.presentation.subscription.params.control.autocomplete.AutoCompleteControl

/**
 * Created by Dimorinny on 12.04.16.
 */
class AutoCompleteControlBuilder(val gson: Gson) : ControlBuilder.Builder {

    override fun build(control: PresentationControl, context: Context): Control {
        var attributes = gson.fromJson(control.attributes, ListAttributes::class.java)
        val dropdown = AutoCompleteControl(context, attributes)
        dropdown.setTitle(control.title)
        return dropdown
    }
}