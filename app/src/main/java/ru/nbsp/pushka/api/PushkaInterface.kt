package ru.nbsp.pushka.api

import retrofit.http.GET
import rx.Observable

/**
 * Created by Dimorinny on 12.02.16.
 */
interface PushkaInterface {

    // Sample
    @GET("/url")
    fun getObjects(): Observable<List<Any>>
}