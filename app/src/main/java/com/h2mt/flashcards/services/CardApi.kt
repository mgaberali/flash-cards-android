package com.h2mt.flashcards.services

import com.h2mt.flashcards.models.Card
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CardApi {

    @GET("card")
    fun getCardsBySetId(@Query("setId") setId: Int) : Call<List<Card>>
}