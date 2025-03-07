package com.pragya.flashcard.repository

import com.pragya.flashcard.model.Flashcard
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.Test

@SpringBootTest
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class FlashcardRepositoryTest @Autowired constructor(
    private val repository: FlashcardRepository
) {

    @BeforeEach
    fun setup() {
        repository.deleteAll().block() // Clear DB before each test
    }

    @Test
    fun `should save and retrieve flashcard`() {
        val flashcard = Flashcard(
            question = "What is Kotlin?",
            answer = "A modern JVM language",
            tags = listOf("Kotlin", "Programming")
        )

        // Save flashcard
        val savedFlashcard = repository.save(flashcard).block()
        assertNotNull(savedFlashcard?.id)

        // Retrieve flashcard
        val retrievedFlashcard = repository.findById(savedFlashcard!!.id!!).block()
        assertEquals("What is Kotlin?", retrievedFlashcard?.question)
        assertEquals("A modern JVM language", retrievedFlashcard?.answer)
    }

    @Test
    fun `should return empty for non-existing flashcard`() {
        val result = repository.findById("non-existent-id").block()
        assertNull(result)
    }

}