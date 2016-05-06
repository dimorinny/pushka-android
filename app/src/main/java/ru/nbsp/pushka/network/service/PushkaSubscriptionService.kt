package ru.nbsp.pushka.network.service

import retrofit2.http.*
import ru.nbsp.pushka.network.request.SubscribeRequest
import ru.nbsp.pushka.network.response.ListResponse
import ru.nbsp.pushka.network.response.SubscriptionIdResponse
import ru.nbsp.pushka.network.response.SubscriptionResponse
import ru.nbsp.pushka.network.response.SubscriptionsResponse
import rx.Observable

/**
 * Created by Dimorinny on 13.03.16.
 */
interface  PushkaSubscriptionService {
    @POST("subscription/")
    fun subscribe(@Body subscribeRequest: SubscribeRequest): Observable<SubscriptionResponse>

    @DELETE("subscription/{id}")
    fun unsubscribe(@Path("id") subscriptionId: String): Observable<SubscriptionIdResponse>

    @GET("subscription/")
    fun getSubscriptions(): Observable<SubscriptionsResponse>

    @GET("subscription/{id}")
    fun getSubscription(@Path("id") subscriptionId: String): Observable<SubscriptionResponse>

    @GET("list/{source_id}/{list_id}")
    fun getListItems(@Path("source_id") sourceId: String,
                  @Path("list_id") listId: String,
                  @Query("query") query: String): Observable<ListResponse>
}