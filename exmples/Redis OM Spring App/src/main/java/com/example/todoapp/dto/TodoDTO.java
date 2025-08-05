package com.example.todoapp.dto;

import com.example.todoapp.model.Todo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data Transfer Objects for Todo operations
 */
public class TodoDTO {
    
    /**
     * DTO for creating a new todo
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Request payload for creating a new todo")
    public static class CreateRequest {
        
        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must not exceed 200 characters")
        @Schema(description = "Todo title", example = "Complete project documentation", required = true)
        private String title;
        
        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        @Schema(description = "Todo description", example = "Write comprehensive documentation for the project")
        private String description;
        
        @Schema(description = "Priority level", example = "HIGH")
        @Builder.Default
        private Todo.Priority priority = Todo.Priority.MEDIUM;
        
        @Schema(description = "Tags associated with the todo", example = "[\"work\", \"documentation\"]")
        private Set<String> tags;
        
        @Schema(description = "Due date for the todo", example = "2024-12-31T23:59:59")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime dueDate;
    }
    
    /**
     * DTO for updating an existing todo
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Request payload for updating a todo")
    public static class UpdateRequest {
        
        @Size(max = 200, message = "Title must not exceed 200 characters")
        @Schema(description = "Todo title", example = "Complete project documentation")
        private String title;
        
        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        @Schema(description = "Todo description", example = "Write comprehensive documentation for the project")
        private String description;
        
        @Schema(description = "Completion status", example = "true")
        private Boolean completed;
        
        @Schema(description = "Priority level", example = "HIGH")
        private Todo.Priority priority;
        
        @Schema(description = "Tags associated with the todo", example = "[\"work\", \"documentation\"]")
        private Set<String> tags;
        
        @Schema(description = "Due date for the todo", example = "2024-12-31T23:59:59")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime dueDate;
    }
    
    /**
     * DTO for todo response
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Todo response payload")
    public static class Response {
        
        @Schema(description = "Todo ID", example = "01HK8X9ABCD123456789")
        private String id;
        
        @Schema(description = "Todo title", example = "Complete project documentation")
        private String title;
        
        @Schema(description = "Todo description", example = "Write comprehensive documentation for the project")
        private String description;
        
        @Schema(description = "Completion status", example = "false")
        private Boolean completed;
        
        @Schema(description = "Priority level", example = "HIGH")
        private Todo.Priority priority;
        
        @Schema(description = "Tags associated with the todo", example = "[\"work\", \"documentation\"]")
        private Set<String> tags;
        
        @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdAt;
        
        @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime updatedAt;
        
        @Schema(description = "Due date for the todo", example = "2024-12-31T23:59:59")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime dueDate;
    }
    
    /**
     * DTO for search request
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Search request parameters")
    public static class SearchRequest {
        
        @Schema(description = "Search term for title and description", example = "documentation")
        private String searchTerm;
        
        @Schema(description = "Filter by completion status", example = "false")
        private Boolean completed;
        
        @Schema(description = "Filter by priority", example = "HIGH")
        private Todo.Priority priority;
        
        @Schema(description = "Filter by tag", example = "work")
        private String tag;
        
        @Schema(description = "Filter todos due before this date", example = "2024-12-31T23:59:59")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime dueBefore;
    }
}