package com.pragya.flashcard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.pragya.flashcard"])
class FlashCardApiApplication

fun main(args: Array<String>) {
	runApplication<FlashCardApiApplication>(*args)
}
