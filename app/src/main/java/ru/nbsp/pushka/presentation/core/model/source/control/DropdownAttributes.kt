package ru.nbsp.pushka.presentation.core.model.source.control

import com.google.gson.annotations.SerializedName

/**
 * Created by egor on 17.03.16.
 */
data class DropdownAttributes(
        @SerializedName("options") val options: List<Option>
)
