package com.example.todoapp;

import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Main Spring Boot Application class for Redis OM Todo Demo
 */
@SpringBootApplication
@EnableRedisDocumentRepositories(basePackages = "com.example.todoapp")
@ComponentScan(basePackages = "com.example.todoapp")
@EntityScan(basePackages = "com.example.todoapp.model")
@RequiredArgsConstructor
@Slf4j
public class TodoApplication {
    
    private final TodoRepository todoRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
        log.info("🚀 Redis OM Spring Todo Application started successfully!");
        log.info("📚 Swagger UI available at: http://localhost:8080/swagger-ui.html");
        log.info("📖 API Documentation available at: http://localhost:8080/api-docs");
    }
    
    /**
     * Load sample data on application startup
     */
    @Bean
    CommandLineRunner loadSampleData() {
        return args -> {
            log.info("🔄 Loading sample data...");
            
            // Clear existing data
            todoRepository.deleteAll();
            
            // Create sample todos
            Todo todo1 = Todo.builder()
                    .title("Complete Redis OM Spring Documentation")
                    .description("Write comprehensive documentation for the Redis OM Spring todo application")
                    .priority(Todo.Priority.HIGH)
                    .tags(Set.of("documentation", "work", "redis"))
                    .dueDate(LocalDateTime.now().plusDays(7))
                    .completed(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            Todo todo2 = Todo.builder()
                    .title("Setup CI/CD Pipeline")
                    .description("Configure GitHub Actions for automated testing and deployment")
                    .priority(Todo.Priority.MEDIUM)
                    .tags(Set.of("devops", "automation", "github"))
                    .dueDate(LocalDateTime.now().plusDays(5))
                    .completed(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            Todo todo3 = Todo.builder()
                    .title("Learn Redis Vector Search")
                    .description("Study and implement vector similarity search capabilities")
                    .priority(Todo.Priority.LOW)
                    .tags(Set.of("learning", "redis", "ai"))
                    .dueDate(LocalDateTime.now().plusDays(14))
                    .completed(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            Todo todo4 = Todo.builder()
                    .title("Write Unit Tests")
                    .description("Create comprehensive unit tests for all service methods")
                    .priority(Todo.Priority.URGENT)
                    .tags(Set.of("testing", "quality", "development"))
                    .dueDate(LocalDateTime.now().plusDays(2))
                    .completed(true)
                    .createdAt(LocalDateTime.now().minusDays(1))
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            Todo todo5 = Todo.builder()
                    .title("Create LinkedIn Post")
                    .description("Share the Redis OM Spring demo application on LinkedIn")
                    .priority(Todo.Priority.MEDIUM)
                    .tags(Set.of("social", "networking", "promotion"))
                    .dueDate(LocalDateTime.now().plusDays(3))
                    .completed(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            // Save sample todos
            todoRepository.save(todo1);
            todoRepository.save(todo2);
            todoRepository.save(todo3);
            todoRepository.save(todo4);
            todoRepository.save(todo5);
            
            log.info("✅ Sample data loaded successfully!");
            log.info("📊 Total todos created: {}", todoRepository.count());
        };
    }
}