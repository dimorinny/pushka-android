package ru.nbsp.pushka.util

import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 01.04.16.
 */
@Singleton
class NotificationIdUtils @Inject constructor() {
    private val counter = AtomicInteger(0)

    fun getNotificationId(): Int {
        return counter.incrementAndGet()
    }
}