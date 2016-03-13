package ru.nbsp.pushka.bus.event

/**
 * Created by Dimorinny on 13.03.16.
 */
sealed class LoadSourcesEvent {
    class Success() : LoadSourcesEvent()
    class Error(val t: Throwable) : LoadSourcesEvent()
}