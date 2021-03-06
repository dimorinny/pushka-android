package ru.nbsp.pushka.network.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.nbsp.pushka.network.response.CategoriesResponse
import ru.nbsp.pushka.network.response.SourceResponse
import ru.nbsp.pushka.network.response.SourcesResponse
import rx.Observable

/**
 * Created by Dimorinny on 28.02.16.
 */
interface PushkaSourceService {
    @GET("source")
    fun getSources(@Query("category") categoryId: String): Observable<SourcesResponse>

    @GET("source/{id}")
    fun getSource(@Path("id") id: String): Observable<SourceResponse>

    @GET("category/")
    fun getCategories(): Observable<CategoriesResponse>
}