package ru.nbsp.pushka.presentation.subscription.params.builders


import android.content.Context
import com.google.gson.Gson
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import ru.nbsp.pushka.presentation.subscription.params.builders.autocomplete.AutoCompleteControlBuilder
import ru.nbsp.pushka.presentation.subscription.params.builders.dropdown.DropdownControlBuilder
import ru.nbsp.pushka.presentation.subscription.params.builders.number.NumberControlBuilder
import ru.nbsp.pushka.presentation.subscription.params.builders.text.TextControlBuilder
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by egor on 16.03.16.
 */

@Singleton
class ControlBuilder @Inject constructor(val gson: Gson) {

    interface Builder {
        open fun build(control: PresentationControl, context: Context): Control
    }

    companion object {
        const val DROPDOWN_ATTRIBUTE = "dropdown"
        const val INPUT_NUMBER_ATTRIBUTE = "number_input"
        const val INPUT_LIST_ATTRIBUTE = "list"
        const val INPUT_TEXT_ATTRIBUTE = "text_input"
    }

    val typeMap: Map<String, Builder> = mapOf(
            Pair(DROPDOWN_ATTRIBUTE, DropdownControlBuilder(gson)),
            Pair(INPUT_NUMBER_ATTRIBUTE, NumberControlBuilder(gson)),
            Pair(INPUT_LIST_ATTRIBUTE, AutoCompleteControlBuilder(gson)),
            Pair(INPUT_TEXT_ATTRIBUTE, TextControlBuilder(gson))
    )

    fun build(control: PresentationControl, context: Context): Control {
        return typeMap[control.type]!!.build(control, context)
    }
}
