package ru.nbsp.pushka.presentation.core.model.device

import ru.nbsp.pushka.R

/**
 * Created by Dimorinny on 07.04.16.
 */
sealed class PresentationDeviceType(val title: String, val icon: Int, val color: Int) {
    class Telegram() : PresentationDeviceType("Telegram", R.drawable.ic_telegram_white_24dp, R.color.telegram)
}