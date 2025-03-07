package com.pragya.flashcard.controller

import com.pragya.flashcard.model.Flashcard
import com.pragya.flashcard.service.FlashcardService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/flashcards")
class FlashcardController(private val service: FlashcardService) {

    @GetMapping
    fun getAllFlashcards(): Flux<Flashcard> = service.getAllFlashcards()

    @GetMapping("/{id}")
    fun getFlashcardById(@PathVariable id: String): Mono<Flashcard> = service.getFlashcardById(id)

    @PostMapping
    fun createFlashcard(@RequestBody flashcard: Flashcard): Mono<Flashcard> = service.createFlashcard(flashcard)

    @PutMapping("/{id}")
    fun updateFlashcard(@PathVariable id: String, @RequestBody flashcard: Flashcard): Mono<Flashcard> =
        service.updateFlashcard(id, flashcard)

    @DeleteMapping("/{id}")
    fun deleteFlashcard(@PathVariable id: String): Mono<Void> = service.deleteFlashcard(id)


}