package com.example.todoapp.repository;

import com.example.todoapp.model.Todo;
import com.redis.om.spring.annotations.Query;
import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repository interface for Todo entities using Redis OM Spring
 */
@Repository
public interface TodoRepository extends RedisDocumentRepository<Todo, String> {
    
    // Find by title (exact match)
    Optional<Todo> findByTitle(String title);
    
    // Find all completed/incomplete todos
    List<Todo> findByCompleted(Boolean completed);
    
    // Find by priority
    List<Todo> findByPriority(Todo.Priority priority);
    
    // Find todos containing specific text in title or description
    List<Todo> findByTitleContainingOrDescriptionContaining(String title, String description);
    
    // Find todos created between dates
    List<Todo> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    // Find todos due before a certain date
    List<Todo> findByDueDateBefore(LocalDateTime dueDate);
    
    // Find todos by tags using native Redis query
    @Query("@tags:{$tag}")
    List<Todo> findByTag(@Param("tag") String tag);
    
    // Find todos by multiple tags
    @Query("@tags:{$tags}")
    List<Todo> findByTags(@Param("tags") Set<String> tags);
    
    // Find incomplete todos due soon (using native query)
    @Query("@completed:{false} @dueDate:[($from ($to]")
    List<Todo> findIncompleteTodosDueBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
    
    // Full-text search across title and description
    @Query("(@title:$searchTerm) | (@description:$searchTerm)")
    List<Todo> searchTodos(@Param("searchTerm") String searchTerm);
    
    // Count todos by status
    long countByCompleted(Boolean completed);
    
    // Count todos by priority
    long countByPriority(Todo.Priority priority);
}