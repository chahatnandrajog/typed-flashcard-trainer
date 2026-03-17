# Flashcards Trainer - System Design Diagram

## Architecture Overview

```mermaid
graph TB
    subgraph Client["Client Layer"]
        UI["Frontend Application"]
    end
    
    subgraph API["API Layer"]
        Controller["FlashcardController<br/>@RestController<br/>POST/GET /api/flashcards"]
    end
    
    subgraph Service["Business Logic Layer"]
        Service["FlashcardService<br/>- createFlashcard<br/>- getAllFlashcards<br/>- getFlashcardById"]
    end
    
    subgraph Data["Data Access Layer"]
        Repo["FlashcardRepository<br/>JpaRepository<br/>- save<br/>- findAll<br/>- findById"]
    end
    
    subgraph Models["Data Models"]
        Entity["Flashcard Entity<br/>@Entity<br/>- id<br/>- question<br/>- answer<br/>- correctCount<br/>- incorrectCount"]
        DTO["CreateFlashcardRequest<br/>@NotBlank validations<br/>- question<br/>- answer"]
    end
    
    subgraph DB["Database"]
        Table["flashcards table<br/>MySQL/PostgreSQL"]
    end
    
    UI -->|HTTP Requests<br/>JSON| Controller
    Controller -->|Response| UI
    
    Controller -->|Uses| Service
    Service -->|Calls| Repo
    
    Repo -->|CRUD Operations| Table
    Table -->|Results| Repo
    
    Controller -->|Receives| DTO
    Service -->|Converts to| Entity
    Repo -->|Persists| Entity
    Entity -->|Returns| Service
    Service -->|Returns| Controller
    
    style Client fill:#e1f5ff
    style API fill:#fff3e0
    style Service fill:#f3e5f5
    style Data fill:#e8f5e9
    style Models fill:#fce4ec
    style DB fill:#ede7f6
```

## Layer Details

### 1. **Client Layer**
- Frontend application that makes HTTP requests to the API

### 2. **API Layer** (Controller)
- **Endpoint**: `/api/flashcards`
- **Methods**:
  - `POST` - Create new flashcard
  - `GET` - Retrieved all flashcards
  - `GET /{id}` - Get flashcard by ID
- **CORS**: Enabled for all origins

### 3. **Business Logic Layer** (Service)
- **FlashcardService** handles core business operations
- Validates input and orchestrates repository calls
- Converts DTOs to entities

### 4. **Data Access Layer** (Repository)
- **FlashcardRepository** extends `JpaRepository<Flashcard, Long>`
- Provides CRUD operations and database interactions
- Automatically implements save, findAll, findById, etc.

### 5. **Data Models**
- **Flashcard Entity**: JPA entity mapped to `flashcards` table
  - Properties: id, question, answer, correctCount, incorrectCount
- **CreateFlashcardRequest DTO**: Input validation object
  - Properties: question, answer (both @NotBlank required)

### 6. **Database Layer**
- SQL database (MySQL/PostgreSQL)
- Table: `flashcards` with auto-increment ID

## Data Flow

1. **Create Flashcard**:
   - Frontend sends POST with CreateFlashcardRequest JSON
   - Controller validates and passes to Service
   - Service creates Flashcard entity from DTO
   - Repository persists to database
   - Entity returned to controller, then to frontend

2. **Get All Flashcards**:
   - Frontend sends GET request
   - Controller calls service.getAllFlashcards()
   - Service calls repository.findAll()
   - List of entities returned through layers

3. **Get Flashcard by ID**:
   - Frontend sends GET with ID parameter
   - Service calls repository.findById(id)
   - Returns entity or throws RuntimeException if not found
