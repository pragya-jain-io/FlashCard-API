package com.pragya.flashcard.repository

import com.pragya.flashcard.model.Flashcard
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
//@ActiveProfiles("test")
class FlashcardRepositoryTest @Autowired constructor(
    private val repository: FlashcardRepository
) {

    @BeforeEach
    fun setup() {
        repository.deleteAll().block() // Ensure a clean database before each test
    }

    @Test
    fun saveAndRetrieveFlashcard() {
        val flashcard = Flashcard(
            question = "What is Kotlin?",
            answer = "A modern JVM language",
            tags = listOf("Kotlin", "Programming")
        )

        val savedFlashcard = repository.save(flashcard).block()
        assertNotNull(savedFlashcard?.id, "Saved flashcard should have an ID")

        val retrievedFlashcard = repository.findById(savedFlashcard!!.id!!).block()
        assertNotNull(retrievedFlashcard, "Flashcard should be retrievable by ID")

        with(retrievedFlashcard!!) {
            assertEquals("What is Kotlin?", question)
            assertEquals("A modern JVM language", answer)
        }
    }

    @Test
    fun nonExistingFlashcard() {
        val retrievedFlashcard = repository.findById("non-existent-id").block()
        assertNull(retrievedFlashcard, "Non-existing flashcard should return null")
    }
}
