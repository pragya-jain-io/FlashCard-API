package com.pragya.flashcard.repository

import com.pragya.flashcard.model.Flashcard
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface FlashcardRepository : ReactiveMongoRepository<Flashcard, String> {
    fun findByTagsContaining(tag: String): Flux<Flashcard>
}