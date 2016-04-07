package ru.nbsp.pushka.util

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 07.04.16.
 */
@Singleton
class TelegramUtils @Inject constructor() {

    fun generateSubscribeUrl(userId: String): String {
        return "https://telegram.me/pushkabot?start=$userId"
    }
}