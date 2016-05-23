package ru.nbsp.pushka.network.service

import retrofit2.http.*
import ru.nbsp.pushka.network.response.DeviceIdResponse
import ru.nbsp.pushka.network.response.DevicesResponse
import rx.Observable

/**
 * Created by Dimorinny on 31.03.16.
 */
interface PushkaDeviceService {
    @GET("device")
    fun getDevices(): Observable<DevicesResponse>

    @DELETE("device/{id}")
    fun deleteDevice(@Path("id") id: String): Observable<DeviceIdResponse>

    @FormUrlEncoded
    @PUT("device/{id}")
    fun putDevice(@Path("id") id: String,
                  @Field("type") type: String,
                  @Field("token") token: String,
                  @Field("name") name: String): Observable<Any>
}