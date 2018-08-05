package com.h2mt.flashcards.services

import com.h2mt.flashcards.config.RetrofitConfig
import com.h2mt.flashcards.models.SetCreateRequest

object SetService {

    private val setApi: SetApi = RetrofitConfig.retrofit.create(SetApi::class.java)

    fun getSets() = setApi.getSets()

    fun addSet(request: SetCreateRequest) = setApi.addSet(request)
}