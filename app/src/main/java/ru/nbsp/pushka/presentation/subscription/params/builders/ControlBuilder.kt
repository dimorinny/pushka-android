package ru.nbsp.pushka.presentation.subscription.params.builders


import android.content.Context
import com.google.gson.Gson
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
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
    }

    val typeMap: Map<String, Builder> = mapOf(
            Pair(DROPDOWN_ATTRIBUTE, DropdownControlBuilder(gson))
    )

    fun build(control: PresentationControl, context: Context): Control {
        return typeMap[control.type]!!.build(control, context)
    }
}
