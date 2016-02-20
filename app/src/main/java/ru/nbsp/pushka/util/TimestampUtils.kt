package ru.nbsp.pushka.util

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 20.02.16.
 */
@Singleton
class TimestampUtils @Inject constructor() {
    fun currentTimestamp(): Long {
        return System.currentTimeMillis() / 1000
    }

    fun afterNow(timestamp: Long): Boolean {
        return currentTimestamp() < timestamp
    }
}