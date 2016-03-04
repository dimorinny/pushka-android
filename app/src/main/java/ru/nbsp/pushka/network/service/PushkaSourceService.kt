package ru.nbsp.pushka.network.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.network.response.SourcesResponse
import rx.Observable

/**
 * Created by Dimorinny on 28.02.16.
 */
interface PushkaSourceService {
    @GET("source")
    fun getSources(): Observable<SourcesResponse>

    @POST("subscription/")
    fun subscribe(@Body subscribeRequest: SubscribeRequest): Observable<Any>
}