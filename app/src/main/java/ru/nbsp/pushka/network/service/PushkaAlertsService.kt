package ru.nbsp.pushka.network.service

import retrofit.http.GET
import ru.nbsp.pushka.network.response.AlertsResponse
import rx.Observable

/**
 * Created by Dimorinny on 28.02.16.
 */
interface PushkaAlertsService {
    @GET("/feed/")
    fun getAlerts(): Observable<AlertsResponse>
}