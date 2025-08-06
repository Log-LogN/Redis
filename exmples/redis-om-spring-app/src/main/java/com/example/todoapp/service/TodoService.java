package com.example.todoapp.service;

import com.example.todoapp.dto.TodoDTO;
import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer for Todo operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {
    
    private final TodoRepository todoRepository;
    
    /**
     * Create a new todo
     */
    public TodoDTO.Response createTodo(TodoDTO.CreateRequest request) {
        log.info("Creating new todo with title: {}", request.getTitle());
        
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .tags(request.getTags())
                .dueDate(request.getDueDate())
                .completed(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        Todo savedTodo = todoRepository.save(todo);
        log.info("Created todo with ID: {}", savedTodo.getId());
        
        return mapToResponse(savedTodo);
    }
    
    /**
     * Get all todos
     */
    public List<TodoDTO.Response> getAllTodos() {
        log.info("Fetching all todos");
        List<Todo> todos = (List<Todo>) todoRepository.findAll();
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get todo by ID
     */
    public Optional<TodoDTO.Response> getTodoById(String id) {
        log.info("Fetching todo with ID: {}", id);
        return todoRepository.findById(id)
                .map(this::mapToResponse);
    }
    
    /**
     * Update a todo
     */
    public Optional<TodoDTO.Response> updateTodo(String id, TodoDTO.UpdateRequest request) {
        log.info("Updating todo with ID: {}", id);
        
        return todoRepository.findById(id)
                .map(todo -> {
                    if (request.getTitle() != null) {
                        todo.setTitle(request.getTitle());
                    }
                    if (request.getDescription() != null) {
                        todo.setDescription(request.getDescription());
                    }
                    if (request.getCompleted() != null) {
                        todo.setCompleted(request.getCompleted());
                    }
                    if (request.getPriority() != null) {
                        todo.setPriority(request.getPriority());
                    }
                    if (request.getTags() != null) {
                        todo.setTags(request.getTags());
                    }
                    if (request.getDueDate() != null) {
                        todo.setDueDate(request.getDueDate());
                    }
                    
                    todo.setUpdatedAt(LocalDateTime.now());
                    
                    Todo updatedTodo = todoRepository.save(todo);
                    log.info("Updated todo with ID: {}", updatedTodo.getId());
                    
                    return mapToResponse(updatedTodo);
                });
    }
    
    /**
     * Delete a todo
     */
    public boolean deleteTodo(String id) {
        log.info("Deleting todo with ID: {}", id);
        
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            log.info("Deleted todo with ID: {}", id);
            return true;
        }
        
        log.warn("Todo with ID {} not found for deletion", id);
        return false;
    }
    
    /**
     * Toggle todo completion status
     */
    public Optional<TodoDTO.Response> toggleTodo(String id) {
        log.info("Toggling completion status for todo with ID: {}", id);
        
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setCompleted(!todo.getCompleted());
                    todo.setUpdatedAt(LocalDateTime.now());
                    
                    Todo updatedTodo = todoRepository.save(todo);
                    log.info("Toggled todo with ID: {} to completed: {}", id, updatedTodo.getCompleted());
                    
                    return mapToResponse(updatedTodo);
                });
    }
    
    /**
     * Search todos based on various criteria
     */
    public List<TodoDTO.Response> searchTodos(TodoDTO.SearchRequest request) {
        log.info("Searching todos with criteria: {}", request);
        
        List<Todo> todos;
        
        if (request.getSearchTerm() != null && !request.getSearchTerm().trim().isEmpty()) {
            todos = todoRepository.searchTodos(request.getSearchTerm());
        } else if (request.getCompleted() != null) {
            todos = todoRepository.findByCompleted(request.getCompleted());
        } else if (request.getPriority() != null) {
            todos = todoRepository.findByPriority(request.getPriority());
        } else if (request.getTag() != null && !request.getTag().trim().isEmpty()) {
            todos = todoRepository.findByTag(request.getTag());
        } else if (request.getDueBefore() != null) {
            todos = todoRepository.findByDueDateBefore(request.getDueBefore());
        } else {
            todos = (List<Todo>) todoRepository.findAll();
        }
        
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get todos by completion status
     */
    public List<TodoDTO.Response> getTodosByStatus(boolean completed) {
        log.info("Fetching todos with completed status: {}", completed);
        List<Todo> todos = todoRepository.findByCompleted(completed);
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get todos by priority
     */
    public List<TodoDTO.Response> getTodosByPriority(Todo.Priority priority) {
        log.info("Fetching todos with priority: {}", priority);
        List<Todo> todos = todoRepository.findByPriority(priority);
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get todos by tag
     */
    public List<TodoDTO.Response> getTodosByTag(String tag) {
        log.info("Fetching todos with tag: {}", tag);
        List<Todo> todos = todoRepository.findByTag(tag);
        return todos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get overdue todos
     */
    public List<TodoDTO.Response> getOverdueTodos() {
        log.info("Fetching overdue todos");
        List<Todo> todos = todoRepository.findByDueDateBefore(LocalDateTime.now());
        return todos.stream()
                .filter(todo -> !todo.getCompleted()) // Only incomplete todos
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get todo statistics
     */
    public TodoStats getStats() {
        log.info("Calculating todo statistics");
        
        long totalTodos = todoRepository.count();
        long completedTodos = todoRepository.countByCompleted(true);
        long pendingTodos = todoRepository.countByCompleted(false);
        long highPriorityTodos = todoRepository.countByPriority(Todo.Priority.HIGH);
        long urgentTodos = todoRepository.countByPriority(Todo.Priority.URGENT);
        
        return TodoStats.builder()
                .totalTodos(totalTodos)
                .completedTodos(completedTodos)
                .pendingTodos(pendingTodos)
                .highPriorityTodos(highPriorityTodos)
                .urgentTodos(urgentTodos)
                .build();
    }
    
    /**
     * Map Todo entity to Response DTO
     */
    private TodoDTO.Response mapToResponse(Todo todo) {
        return TodoDTO.Response.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.getCompleted())
                .priority(todo.getPriority())
                .tags(todo.getTags())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .dueDate(todo.getDueDate())
                .build();
    }
    
    /**
     * Statistics DTO
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class TodoStats {
        private long totalTodos;
        private long completedTodos;
        private long pendingTodos;
        private long highPriorityTodos;
        private long urgentTodos;
    }
}