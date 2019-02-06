package com.example.ea_create.momosheduler.Network

import com.example.ea_create.momosheduler.Global
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInit {

    companion object {
        fun retrofit():Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
           return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Global.baseUrl)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .connectTimeout(3, TimeUnit.SECONDS)
                        .build()
                ).build()




        }

    }
}