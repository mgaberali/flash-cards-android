package com.h2mt.flashcards.models

import java.io.Serializable

data class Set (val id: Int, val name: String, val desc: String, val createdAt: String) : Serializable