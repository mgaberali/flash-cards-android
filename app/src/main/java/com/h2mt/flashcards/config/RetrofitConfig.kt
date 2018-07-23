package com.h2mt.flashcards.config

import android.content.Context
import com.h2mt.flashcards.App
import com.h2mt.flashcards.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfig {

    val retrofit: Retrofit

    init {

        val appContext = App.applicationContext()

        val userSharedPref = appContext.getSharedPreferences(appContext
                .getString(R.string.user_preference_file_key), Context.MODE_PRIVATE)

        val accessToken = userSharedPref.getString(appContext.getString(R.string.user_preference_access_token_key), "")

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor({
            val original = it.request()

            val request = original.newBuilder()
                    .header("Authorization", "bearer $accessToken")
                    .method(original.method(), original.body())
                    .build()

            it.proceed(request)
        })

        val client = httpClient.build()

        retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(appContext.resources.getString(R.string.flash_cards_api_url))
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()
    }

}