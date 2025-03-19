package com.pragya.flashcard.controller

import com.pragya.flashcard.model.Flashcard
import com.pragya.flashcard.repository.FlashcardRepository
import com.pragya.flashcard.service.FlashcardService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(FlashcardController::class)
@ExtendWith(SpringExtension::class)
@Import(FlashcardService::class)
//@ActiveProfiles("test")
class FlashcardControllerTest @Autowired constructor(
    private val webTestClient: WebTestClient
) {

    @MockBean
    private lateinit var repository: FlashcardRepository

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun flashcardCreatedSuccess() {
        val flashcard = Flashcard(
            question = "What is Spring Boot?",
            answer = "A framework for building Java applications",
            tags = listOf("Spring", "Java")
        )

        Mockito.`when`(repository.save(Mockito.any())).thenReturn(Mono.just(flashcard))

        webTestClient.post().uri("/api/flashcards")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(flashcard)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.question").isEqualTo(flashcard.question)
    }
}