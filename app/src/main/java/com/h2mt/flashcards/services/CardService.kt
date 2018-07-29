package com.h2mt.flashcards.services

import com.h2mt.flashcards.config.RetrofitConfig
import com.h2mt.flashcards.models.Card
import com.h2mt.flashcards.models.CreateCardRequest

object CardService {

    private val cardApi: CardApi = RetrofitConfig.retrofit.create(CardApi::class.java)

    fun getCardsBySetId(setId: Int) = cardApi.getCardsBySetId(setId)

    fun createCard(request: CreateCardRequest) = cardApi.createCard(request)

    fun deleteCard(cardId: Int) = cardApi.deleteCard(cardId)
}