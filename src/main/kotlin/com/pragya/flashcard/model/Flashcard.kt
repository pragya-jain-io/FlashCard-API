package com.pragya.flashcard.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "flashcards")
data class Flashcard (
    @Id val id: String? = null,
    val question: String,
    val answer: String,
    val tags: List<String> = emptyList(),
    var successAttempts: Int = 0,
    var failureAttempts: Int = 0
){
}