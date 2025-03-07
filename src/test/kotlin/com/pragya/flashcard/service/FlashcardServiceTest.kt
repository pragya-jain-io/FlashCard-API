package com.pragya.flashcard.service

import com.mongodb.client.model.Filters.eq
import com.pragya.flashcard.model.Flashcard
import com.pragya.flashcard.repository.FlashcardRepository
import de.flapdoodle.embed.mongo.packageresolver.DistributionMatch.any
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

import reactor.core.publisher.Mono
import kotlin.test.Test

@SpringBootTest
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class FlashcardServiceTest @Autowired constructor(
    private val service: FlashcardService,
    private val repository: FlashcardRepository
) {

    @BeforeEach
    fun setup() {
        repository.deleteAll().block()
    }

    @Test
    fun createFlashcard() {
        val flashcard = Flashcard(
            question = "What is WebFlux?",
            answer = "A reactive web framework in Spring",
            tags = listOf("Spring", "WebFlux")
        )

        val savedFlashcard = service.createFlashcard(flashcard).block()
        assertNotNull(savedFlashcard?.id)
        assertEquals("What is WebFlux?", savedFlashcard?.question)
    }

    @Test
    fun findbyId() {
        val flashcard = repository.save(
            Flashcard(question = "What is Kotlin?", answer = "A JVM language")
        ).block()!!

        val result = service.getFlashcardById(flashcard.id!!).block()
        assertNotNull(result)
        assertEquals("What is Kotlin?", result?.question)
    }

    @Test
    fun nonExistingFlashcard() {
        val result = service.getFlashcardById("non-existent-id").block()
        assertNull(result)
    }

    @Test
    fun updateFlashcard() {
        val flashcard = repository.save(
            Flashcard(question = "Initial", answer = "Test")
        ).block()!!

        val updatedFlashcard = flashcard.copy(question = "Updated")
        val result = service.updateFlashcard(flashcard.id!!, updatedFlashcard).block()

        assertNotNull(result)
        assertEquals("Updated", result?.question)
    }

    @Test
    fun deleteFlashcard() {
        val flashcard = repository.save(
            Flashcard(question = "To be deleted", answer = "Test")
        ).block()!!

        service.deleteFlashcard(flashcard.id!!).block()
        val result = repository.findById(flashcard.id!!).block()

        assertNull(result)
    }



}
