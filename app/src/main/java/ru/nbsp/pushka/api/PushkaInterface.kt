package ru.nbsp.pushka.api

import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.POST
import ru.nbsp.pushka.api.response.LoginResponse
import rx.Observable

/**
 * Created by Dimorinny on 12.02.16.
 */
interface PushkaInterface {

    @FormUrlEncoded
    @POST("/auth/social")
    fun login(@Field("provider") provider: String,
              @Field("token") token: String): Observable<LoginResponse>
}