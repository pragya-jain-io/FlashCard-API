package com.pragya.flashcard.service

import com.pragya.flashcard.model.Flashcard
import com.pragya.flashcard.repository.FlashcardRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Mono

@SpringBootTest
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ActiveProfiles("test")
class FlashcardServiceTest @Autowired constructor(
    private val service: FlashcardService,
    private val repository: FlashcardRepository
) {

    @BeforeEach
    fun setup() {
        repository.deleteAll().block() // Ensures a clean test environment before each test
    }

    @Test
    fun createFlashcard() {
        val flashcard = Flashcard(
            question = "What is WebFlux?",
            answer = "A reactive web framework in Spring",
            tags = listOf("Spring", "WebFlux")
        )

        val savedFlashcard = service.createFlashcard(flashcard).block()
        assertNotNull(savedFlashcard?.id, "Saved flashcard should have an ID")
        assertEquals("What is WebFlux?", savedFlashcard?.question)
    }

    @Test
    fun findbyId() {
        val flashcard = repository.save(
            Flashcard(question = "What is Kotlin?", answer = "A JVM language")
        ).block()!!

        val result = service.getFlashcardById(flashcard.id!!).block()
        assertNotNull(result, "Flashcard should be retrieved successfully")
        assertEquals("What is Kotlin?", result?.question)
    }

    @Test
    fun nonExistingFlashcard() {
        val result = service.getFlashcardById("non-existent-id").block()
        assertNull(result, "Non-existent flashcard should return null")
    }

    @Test
    fun updateFlashcard() {
        val flashcard = repository.save(
            Flashcard(question = "Initial", answer = "Test")
        ).block()!!

        val updatedFlashcard = flashcard.copy(question = "Updated")
        val result = service.updateFlashcard(flashcard.id!!, updatedFlashcard).block()

        assertNotNull(result, "Updated flashcard should not be null")
        assertEquals("Updated", result?.question)
    }

    @Test
    fun deleteFlashcard() {
        val flashcard = repository.save(
            Flashcard(question = "To be deleted", answer = "Test")
        ).block()!!

        service.deleteFlashcard(flashcard.id!!).block()
        val result = repository.findById(flashcard.id!!).block()

        assertNull(result, "Flashcard should be deleted successfully")
    }
}