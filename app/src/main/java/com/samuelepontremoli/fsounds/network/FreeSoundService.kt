package com.samuelepontremoli.fsounds.network

import com.google.gson.GsonBuilder
import com.samuelepontremoli.fsounds.utils.BASE_URL
import com.samuelepontremoli.fsounds.utils.DEBUG
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * FSounds - Created by s.pontremoli on 24/07/2017.
 */
interface FreeSoundService {

    @GET("apiv2/search/text/")
    fun getTextSearch(@Query("query") query: String, @Query("token") token: String): Flowable<SearchResult>

    @GET("/apiv2/search/target/")
    fun getTextFilterSearch(@Query("filter") filter: String, @Query("token") token: String): Flowable<SearchResult>

    @GET("apiv2/sounds/{id}/")
    fun getSoundDetail(@Path("id") id: Int, @Query("token") token: String): Flowable<SoundDetail>

    companion object Factory {

        fun create(): FreeSoundService {

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = if (DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(loggingInterceptor)

            val gson = GsonBuilder().create()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build()

            return retrofit.create(FreeSoundService::class.java)
        }

    }

}