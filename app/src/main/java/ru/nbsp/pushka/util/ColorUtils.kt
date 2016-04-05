package ru.nbsp.pushka.util

import android.graphics.Color
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 06.04.16.
 */
@Singleton
class ColorUtils @Inject constructor() {
    fun darker(color: Int, factor: Float = 0.87F): Int {
        val a = Color.alpha(color);
        val r = Color.red(color);
        val g = Color.green(color);
        val b = Color.blue(color);

        return Color.argb(a,
                Math.max((r * factor).toInt(), 0),
                Math.max((g * factor).toInt(), 0),
                Math.max((b * factor).toInt(), 0));
    }
}