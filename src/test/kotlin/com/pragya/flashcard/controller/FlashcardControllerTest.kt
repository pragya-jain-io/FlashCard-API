package com.pragya.flashcard.controller

import com.pragya.flashcard.model.Flashcard
import com.pragya.flashcard.repository.FlashcardRepository
import com.pragya.flashcard.service.FlashcardService

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
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

import kotlin.test.Test

@WebFluxTest(FlashcardController::class)
@ExtendWith(SpringExtension::class)
@Import(FlashcardService::class)
@ActiveProfiles("test")
class FlashcardControllerTest @Autowired constructor(
    private val webTestClient: WebTestClient,
//    private val repository: FlashcardRepository
)  {
    @MockBean
    private lateinit var repository: FlashcardRepository

    @BeforeEach
    fun setup() {
//        repository.deleteAll().block()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should create a flashcard`() {
        val request = Flashcard(
            question = "What is Spring Boot?",
            answer = "A framework for building Java applications",
            tags = listOf("Spring", "Java")
        )
//        Mockito.`when`(repository.save(any())).thenReturn(Mono.just(request))
        Mockito.`when`(repository.save(any<Flashcard>())).thenReturn(Mono.just(request))
        webTestClient.post().uri("/api/flashcards")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.question").isEqualTo("What is Spring Boot?")
    }



}