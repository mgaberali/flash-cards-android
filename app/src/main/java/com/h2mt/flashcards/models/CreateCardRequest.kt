package com.h2mt.flashcards.models

data class CreateCardRequest (val term: String, val definition: String, val setId: Int)