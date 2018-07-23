package com.h2mt.flashcards.services

import com.h2mt.flashcards.config.RetrofitConfig

object CardService {

    private val cardApi: CardApi = RetrofitConfig.retrofit.create(CardApi::class.java)

    fun getCardsBySetId(setId: Int) = cardApi.getCardsBySetId(setId)
}