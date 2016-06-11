package ru.nbsp.pushka.network.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.nbsp.pushka.network.response.AlertResponse
import ru.nbsp.pushka.network.response.AlertsResponse
import rx.Observable

/**
 * Created by Dimorinny on 28.02.16.
 */
interface PushkaAlertsService {
    @GET("feed/")
    fun getAlerts(): Observable<AlertsResponse>

    @GET("feed/")
    fun getMoreAlerts(@Query("time") firstItemTime: Long, @Query("offset") offset: Int): Observable<AlertsResponse>

    @GET("feed/{id}")
    fun getAlert(@Path("id") id: String): Observable<AlertResponse>
}