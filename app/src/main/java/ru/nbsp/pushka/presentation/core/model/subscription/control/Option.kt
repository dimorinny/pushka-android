package ru.nbsp.pushka.presentation.core.model.subscription.control

import com.google.gson.annotations.SerializedName

/**
 * Created by Dimorinny on 09.04.16.
 */
data class Option(
        @SerializedName("name") val name: String,
        @SerializedName("value") val value: String?) {

    override fun toString(): String = name
}
