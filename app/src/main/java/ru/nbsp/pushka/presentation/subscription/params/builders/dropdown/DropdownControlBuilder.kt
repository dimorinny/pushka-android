package ru.nbsp.pushka.presentation.subscription.params.builders.dropdown

import android.content.Context
import com.google.gson.Gson
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import ru.nbsp.pushka.presentation.core.model.source.control.DropdownAttributes
import ru.nbsp.pushka.presentation.subscription.params.builders.ControlBuilder
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.presentation.subscription.params.control.dropdown.DropdownControl

/**
 * Created by egor on 17.03.16.
 */

class DropdownControlBuilder(val gson: Gson) : ControlBuilder.Builder {

    override fun build(control: PresentationControl, context: Context): Control {
        var attributes = gson.fromJson(control.attributes, DropdownAttributes::class.java)
        val dropdown = DropdownControl(context, attributes.options)
        dropdown.setTitle(control.title)
        return dropdown
    }
}