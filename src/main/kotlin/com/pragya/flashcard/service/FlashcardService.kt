package com.pragya.flashcard.service

import com.pragya.flashcard.model.Flashcard
import com.pragya.flashcard.repository.FlashcardRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class FlashcardService( private val flashcardRepository: FlashcardRepository) {

    fun getAllFlashcards(): Flux<Flashcard> = flashcardRepository.findAll()

    fun getFlashcardById(id: String): Mono<Flashcard> = flashcardRepository.findById(id)

    fun createFlashcard(flashcard: Flashcard): Mono<Flashcard> = flashcardRepository.save(flashcard)

    fun updateFlashcard(id: String, updatedFlashcard: Flashcard): Mono<Flashcard> {
        return flashcardRepository.findById(id)
            .flatMap { existing ->
                val updated = existing.copy(
                    question = updatedFlashcard.question,
                    answer = updatedFlashcard.answer,
                    tags = updatedFlashcard.tags
                )
                flashcardRepository.save(updated)
            }
    }

    fun deleteFlashcard(id: String): Mono<Void> = flashcardRepository.deleteById(id)

    fun getRandomFlashcard(): Mono<Flashcard> = flashcardRepository.findAll().collectList()
        .flatMap { list ->
            if (list.isNotEmpty()) Mono.just(list.random()) else Mono.empty()
        }

    fun getFlashcardsByTag(tag: String): Flux<Flashcard> =
        flashcardRepository.findByTagsContaining(tag)

}