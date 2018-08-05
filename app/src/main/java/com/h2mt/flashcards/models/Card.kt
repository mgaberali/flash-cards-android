package com.h2mt.flashcards.models

data class Card (val id: Int, val term: String, val definition: String, val imageUrl: String?, val setId: Int)