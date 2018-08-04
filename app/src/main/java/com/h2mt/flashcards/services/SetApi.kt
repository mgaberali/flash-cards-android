package com.h2mt.flashcards.services

import com.h2mt.flashcards.models.Card
import com.h2mt.flashcards.models.Set
import retrofit2.Call
import retrofit2.http.GET

interface SetApi {
    @GET("set")
    fun getSets() : Call<List<Card>>
}