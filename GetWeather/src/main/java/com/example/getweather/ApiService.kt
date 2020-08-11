package com.example.getweather

import com.example.getweather.dto.Location
import com.example.getweather.dto.Weather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://www.metaweather.com/"
//Get Location
///https://www.metaweather.com/api/location/search/?lattlong=56.85836,35.90057

///Get weather
//https://www.metaweather.com/api/location/2122265/
interface ApiService{

    @GET("api/location/search")
    suspend fun getLocation(@Query("query") location: String?) : Location

    @GET("api/location/{woeid}/")
     suspend fun getWeather(@Path("woeid") woeid: Int?) : Weather


    companion object{
        operator fun invoke(): ApiService {

            val interceptor =  HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}