package ru.nbsp.pushka.presentation.subscription.params.inflaters

import android.content.Context
import android.util.Log
import com.google.gson.Gson


import com.google.gson.JsonArray
import com.google.gson.JsonObject

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import java.util.*

/**
 * Created by egor on 16.03.16.
 */
object ControlInflater {

    interface Inflater {
        open fun inflate(control: PresentationControl, context: Context): Control
    }

    val typeMap: Map<String, Inflater> = mapOf(
            Pair("dropdown", DropdownControlInflater)
    )

    fun inflate(control: PresentationControl, context: Context): Control {
        return typeMap[control.type]!!.inflate(control, context)
    }

}
