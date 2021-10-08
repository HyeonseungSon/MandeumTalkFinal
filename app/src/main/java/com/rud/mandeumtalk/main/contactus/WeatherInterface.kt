package com.rud.mandeumtalk.main.contactus

import android.util.Log
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

val num_of_rows : Int = 10
val page_no : Int = 1
val data_type : String = "JSON"
val base_time : String = "0200"
val base_date : Int = getDate()
val nx : String = "55"
val ny : String = "127"

interface WeatherInterface {

    @GET("getVilageFcst?serviceKey=njQbnx8IAOHP0Poyx%2Bf67P9Hmmx%2BgVpn3wJCiT8nvvgQraNag3O8CLD84XjD2vA7zZRXuDLlUliy9T%2BZG4YwVw%3D%3D")

    fun GetWeather (@Query("dataType") data_type : String,
                    @Query("numOfRows") num_of_rows : Int,
                    @Query("pageNo") page_no : Int,
                    @Query("base_date") base_date : Int,
                    @Query("base_time") base_time : String,
                    @Query("nx") nx : String,
                    @Query("ny") ny : String
    ) : Call<WeatherDataModel>
}

fun getDate () : Int {

    val long_now = System.currentTimeMillis()
    val t_date = Date(long_now)

    val t_dateFormat = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR"))

    val str_date = t_dateFormat.format(t_date).toInt()

    Log.d("str_date1", str_date.toString())

    return str_date
}

fun getTime () : Int {

    val long_now = System.currentTimeMillis()
    val t_time = Date(long_now)

    val t_timeFormat = SimpleDateFormat("kkmm", Locale("ko", "KR"))

    val str_time = t_timeFormat.format(t_time).toInt()

    if (210 < str_time && str_time <= 510) {
    }

    return str_time
}