# Flashcard API

This is a **Reactive REST API** built using **Kotlin, Spring Boot, WebFlux, and MongoDB** for managing flashcards.

## Features
- Create, Read, Update, and Delete (CRUD) operations for flashcards
- Reactive programming with WebFlux
- MongoDB for the database
- Unit and integration tests

## Tech Stack
- **Kotlin**
- **Spring Boot**
- **Spring WebFlux**
- **MongoDB**
- **Gradle**

## API Endpoints

### Flashcards
Base URL: `/api/flashcards`

| Method | Endpoint           | Description |
|--------|-------------------|-------------|
| GET    | `/api/flashcards` | Get all flashcards |
| GET    | `/api/flashcards/{id}` | Get a flashcard by ID |
| POST   | `/api/flashcards` | Create a new flashcard |
| PUT    | `/api/flashcards/{id}` | Update an existing flashcard |
| DELETE | `/api/flashcards/{id}` | Delete a flashcard |
| GET    | `/api/flashcards/random` | Get a random flashcard |
| GET    | `/api/flashcards/tags/{tag}` | Get flashcards by tag |

---
