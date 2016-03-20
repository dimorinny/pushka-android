package ru.nbsp.pushka.bus.event

/**
 * Created by Dimorinny on 02.03.16.
 */
sealed class LoadSimpleListEvent {
    class Success() : LoadSimpleListEvent()
    class Error(val t: Throwable) : LoadSimpleListEvent()
}