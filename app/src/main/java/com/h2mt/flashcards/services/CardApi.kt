package com.h2mt.flashcards.services

import com.h2mt.flashcards.models.Card
import com.h2mt.flashcards.models.CreateCardRequest
import retrofit2.Call
import retrofit2.http.*

interface CardApi {

    @GET("card")
    fun getCardsBySetId(@Query("setId") setId: Int) : Call<List<Card>>

    @POST("card")
    fun createCard(@Body request: CreateCardRequest) : Call<Void>

    @DELETE("card/{cardId}")
    fun deleteCard(@Path("cardId") cardId: Int) : Call<Void>
}