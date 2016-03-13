package ru.nbsp.pushka.network.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.network.response.SubscriptionsResponse
import rx.Observable

/**
 * Created by Dimorinny on 13.03.16.
 */
interface  PushkaSubscriptionService {
    @POST("subscription/")
    fun subscribe(@Body subscribeRequest: SubscribeRequest): Observable<Any>

    @GET("subscription/")
    fun getSubscriptions(@Query("detail") detail: Boolean): Observable<SubscriptionsResponse>
}