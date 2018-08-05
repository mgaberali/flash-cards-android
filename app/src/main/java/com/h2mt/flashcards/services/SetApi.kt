package com.h2mt.flashcards.services

import com.h2mt.flashcards.models.Card
import com.h2mt.flashcards.models.Set
import com.h2mt.flashcards.models.SetCreateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SetApi {
    @GET("set")
    fun getSets() : Call<List<Set>>

    @POST("set")
    fun addSet(@Body request: SetCreateRequest): Call<Void>
}