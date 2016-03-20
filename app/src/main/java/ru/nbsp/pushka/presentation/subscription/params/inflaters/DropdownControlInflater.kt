package ru.nbsp.pushka.presentation.subscription.params.inflaters

import android.app.Presentation
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import ru.nbsp.pushka.presentation.core.model.source.control.DropdownAttributes
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.presentation.subscription.params.control.DropdownControl

/**
 * Created by egor on 17.03.16.
 */
object DropdownControlInflater : ControlInflater.Inflater {
    override fun inflate(control: PresentationControl, context: Context): Control {
        val jsonAttrs = control.attributes
        val dropdown = DropdownControl(context)
        var gson: Gson = Gson()
        var attrs: DropdownAttributes = gson.fromJson(jsonAttrs,
                DropdownAttributes::class.java)

        for(option in attrs.options) {
            dropdown.addOption(option.name, option.value)
        }
        dropdown.setTitle(control.title)
        return dropdown
    }
}