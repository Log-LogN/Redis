# Redis OM Spring Todo Demo Application

A comprehensive Todo CRUD application demonstrating the power of **Redis OM Spring** with advanced features like full-text search, secondary indexing, and sophisticated querying capabilities.

## 🚀 Features

- **Complete CRUD Operations** - Create, Read, Update, Delete todos
- **Advanced Search** - Full-text search across title and description
- **Filtering & Indexing** - Filter by status, priority, tags, and due dates
- **Redis JSON Documents** - Store todos as JSON documents using RedisJSON
- **Secondary Indexing** - Fast queries using RediSearch
- **Swagger Documentation** - Interactive API documentation
- **Sample Data** - Pre-loaded sample todos for testing
- **Statistics API** - Get insights about your todos

## 🛠️ Technology Stack

- **Spring Boot 3.3.0** - Application framework
- **Redis OM Spring 1.0.0-RC.1** - Object mapping and advanced Redis features
- **Java 17** - Programming language
- **Maven** - Build tool
- **Swagger/OpenAPI 3** - API documentation
- **Lombok** - Reduce boilerplate code
- **Redis Stack** - Redis with search and JSON capabilities

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Redis Stack** installed locally (or Redis with RediSearch and RedisJSON modules)

### Redis Installation (Windows 11)

Since you mentioned you have Redis installed on Windows 11, make sure you have Redis Stack or Redis with the required modules:

1. **Redis Stack** (Recommended):
   ```bash
   # If using Redis Stack, it includes all required modules
   redis-stack-server
   ```

2. **Or Redis with modules**:
    - Ensure RediSearch and RedisJSON modules are loaded
    - Redis should be running on `localhost:6379`

## 🚀 Quick Start

### 1. Clone and Setup

```bash
# Create project directory
mkdir redis-om-todo-demo
cd redis-om-todo-demo

# Copy all the provided files into their respective directories:
# src/main/java/com/example/todoapp/
# src/main/resources/
# pom.xml
```

### 2. Project Structure

```
redis-om-todo-demo/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/todoapp/
│       │       ├── TodoApplication.java
│       │       ├── controller/
│       │       │   └── TodoController.java
│       │       ├── dto/
│       │       │   └── TodoDTO.java
│       │       ├── exception/
│       │       │   └── GlobalExceptionHandler.java
│       │       ├── model/
│       │       │   └── Todo.java
│       │       ├── repository/
│       │       │   └── TodoRepository.java
│       │       └── service/
│       │           └── TodoService.java
│       └── resources/
│           └── application.properties
└── README.md
```

### 3. Build and Run

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access the Application

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Documentation**: http://localhost:8080/api-docs
- **Base API URL**: http://localhost:8080/api/todos

## 📚 API Endpoints

### Core CRUD Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/todos` | Create a new todo |
| `GET` | `/api/todos` | Get all todos |
| `GET` | `/api/todos/{id}` | Get todo by ID |
| `PUT` | `/api/todos/{id}` | Update a todo |
| `DELETE` | `/api/todos/{id}` | Delete a todo |
| `PATCH` | `/api/todos/{id}/toggle` | Toggle completion status |

### Advanced Querying

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/todos/search` | Advanced search with multiple criteria |
| `GET` | `/api/todos/status/{completed}` | Filter by completion status |
| `GET` | `/api/todos/priority/{priority}` | Filter by priority level |
| `GET` | `/api/todos/tag/{tag}` | Filter by tag |
| `GET` | `/api/todos/overdue` | Get overdue todos |
| `GET` | `/api/todos/stats` | Get todo statistics |

## 🎯 Key Redis OM Spring Features Demonstrated

### 1. Document Mapping
```java
@Document
public class Todo {
    @Id private String id;
    @Searchable private String title;
    @Indexed private Boolean completed;
    // ...
}
```

### 2. Repository with Custom Queries
```java
public interface TodoRepository extends RedisDocumentRepository<Todo, String> {
    List<Todo> findByCompleted(Boolean completed);
    
    @Query("@tags:{$tag}")
    List<Todo> findByTag(@Param("tag") String tag);
}
```

### 3. Full-Text Search
```java
@Query("(@title:$searchTerm) | (@description:$searchTerm)")
List<Todo> searchTodos(@Param("searchTerm") String searchTerm);
```

### 4. Secondary Indexing
- **@Indexed**: For exact matches and range queries
- **@Searchable**: For full-text search capabilities

## 🔧 Configuration

### Redis Connection (application.properties)
```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.data.redis.password=
spring.data.redis.username=default
```

### Enable Redis Document Repositories
```java
@EnableRedisDocumentRepositories(basePackages = "com.example.todoapp.repository")
```

## 📊 Sample Data

The application loads sample todos on startup:

1. **High Priority**: Complete Redis OM Spring Documentation
2. **Medium Priority**: Setup CI/CD Pipeline
3. **Low Priority**: Learn Redis Vector Search
4. **Urgent**: Write Unit Tests (Completed)
5. **Medium Priority**: Create LinkedIn Post

## 🧪 Testing the API

### Using Swagger UI
1. Navigate to http://localhost:8080/swagger-ui.html
2. Explore and test all endpoints interactively
3. View request/response schemas

### Using cURL

```bash
# Create a todo
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Redis OM Spring",
    "description": "Master the Redis OM Spring framework",
    "priority": "HIGH",
    "tags": ["learning", "redis"],
    "dueDate": "2024-12-31T23:59:59"
  }'

# Get all todos
curl http://localhost:8080/api/todos

# Search todos
curl -X POST http://localhost:8080/api/todos/search \
  -H "Content-Type: application/json" \
  -d '{"searchTerm": "redis"}'

# Get statistics
curl http://localhost:8080/api/todos/stats
```

## 💡 Key Learning Points

1. **Redis OM Spring** simplifies Redis integration with Spring Boot
2. **@Document** annotation maps POJOs to Redis JSON documents
3. **Secondary indexing** enables fast queries beyond primary keys
4. **Full-text search** powered by RediSearch module
5. **ULID** provides better performance than UUID for ID generation
6. **Declarative queries** reduce boilerplate code significantly

## 🔍 Redis OM Spring Advantages

- **Type Safety**: Strongly typed queries and operations
- **Automatic Indexing**: Declarative index creation
- **Spring Data Integration**: Familiar repository patterns
- **Advanced Search**: Full-text and geo-spatial search capabilities
- **Performance**: Leverages Redis's in-memory speed
- **Scalability**: Redis's horizontal scaling capabilities
