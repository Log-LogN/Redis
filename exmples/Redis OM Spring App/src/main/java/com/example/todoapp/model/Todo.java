package com.example.todoapp.model;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Todo entity representing a task item stored as Redis JSON document
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Todo {
    
    @Id
    private String id;
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    @Searchable
    private String title;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Searchable
    private String description;
    
    @Indexed
    @Builder.Default
    private Boolean completed = false;
    
    @Indexed
    @Builder.Default
    private Priority priority = Priority.MEDIUM;
    
    @Indexed
    private Set<String> tags;
    
    @CreatedDate
    @Indexed
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Indexed
    private LocalDateTime updatedAt;
    
    @Indexed
    private LocalDateTime dueDate;
    
    /**
     * Priority levels for todos
     */
    public enum Priority {
        LOW, MEDIUM, HIGH, URGENT
    }
}