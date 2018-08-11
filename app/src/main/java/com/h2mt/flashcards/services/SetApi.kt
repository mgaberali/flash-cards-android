package com.h2mt.flashcards.services

import com.h2mt.flashcards.models.Card
import com.h2mt.flashcards.models.Set
import com.h2mt.flashcards.models.SetCreateRequest
import retrofit2.Call
import retrofit2.http.*

interface SetApi {
    @GET("set")
    fun getSets() : Call<List<Set>>

    @POST("set")
    fun addSet(@Body request: SetCreateRequest): Call<Integer>

    @DELETE("set/{setId}")
    fun deleteSet(@Path("setId") userId : Integer): Call<Void>

    @PUT("set/{setId}")
    fun updateSet(@Path("setId") userId: Integer, @Body request: SetCreateRequest): Call<Void>

}