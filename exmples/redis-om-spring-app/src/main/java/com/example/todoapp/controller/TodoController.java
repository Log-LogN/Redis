package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoDTO;
import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Todo operations
 */
@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Todo Management", description = "API for managing todos using Redis OM Spring")
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "Create a new todo", description = "Creates a new todo item and stores it in Redis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Todo created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<TodoDTO.Response> createTodo(
            @Valid @RequestBody TodoDTO.CreateRequest request) {
        log.info("POST /api/todos - Creating new todo");
        TodoDTO.Response response = todoService.createTodo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all todos", description = "Retrieves all todos from Redis")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved todos")
    @GetMapping
    public ResponseEntity<List<TodoDTO.Response>> getAllTodos() {
        log.info("GET /api/todos - Fetching all todos");
        List<TodoDTO.Response> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Get todo by ID", description = "Retrieves a specific todo by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo found"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO.Response> getTodoById(
            @Parameter(description = "Todo ID", example = "01HK8X9ABCD123456789")
            @PathVariable String id) {
        log.info("GET /api/todos/{} - Fetching todo by ID", id);
        return todoService.getTodoById(id)
                .map(todo -> ResponseEntity.ok(todo))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update a todo", description = "Updates an existing todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo updated successfully"),
            @ApiResponse(responseCode = "404", description = "Todo not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO.Response> updateTodo(
            @Parameter(description = "Todo ID", example = "01HK8X9ABCD123456789")
            @PathVariable String id,
            @Valid @RequestBody TodoDTO.UpdateRequest request) {
        log.info("PUT /api/todos/{} - Updating todo", id);
        return todoService.updateTodo(id, request)
                .map(todo -> ResponseEntity.ok(todo))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a todo", description = "Deletes a todo by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Todo deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @Parameter(description = "Todo ID", example = "01HK8X9ABCD123456789")
            @PathVariable String id) {
        log.info("DELETE /api/todos/{} - Deleting todo", id);
        boolean deleted = todoService.deleteTodo(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Toggle todo completion", description = "Toggles the completion status of a todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo status toggled successfully"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TodoDTO.Response> toggleTodo(
            @Parameter(description = "Todo ID", example = "01HK8X9ABCD123456789")
            @PathVariable String id) {
        log.info("PATCH /api/todos/{}/toggle - Toggling todo completion", id);
        return todoService.toggleTodo(id)
                .map(todo -> ResponseEntity.ok(todo))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Search todos", description = "Search todos based on various criteria")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    @PostMapping("/search")
    public ResponseEntity<List<TodoDTO.Response>> searchTodos(
            @RequestBody TodoDTO.SearchRequest request) {
        log.info("POST /api/todos/search - Searching todos");
        List<TodoDTO.Response> todos = todoService.searchTodos(request);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Get todos by status", description = "Retrieves todos filtered by completion status")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved todos by status")
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<TodoDTO.Response>> getTodosByStatus(
            @Parameter(description = "Completion status", example = "false")
            @PathVariable boolean completed) {
        log.info("GET /api/todos/status/{} - Fetching todos by status", completed);
        List<TodoDTO.Response> todos = todoService.getTodosByStatus(completed);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Get todos by priority", description = "Retrieves todos filtered by priority level")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved todos by priority")
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TodoDTO.Response>> getTodosByPriority(
            @Parameter(description = "Priority level", example = "HIGH")
            @PathVariable Todo.Priority priority) {
        log.info("GET /api/todos/priority/{} - Fetching todos by priority", priority);
        List<TodoDTO.Response> todos = todoService.getTodosByPriority(priority);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Get todos by tag", description = "Retrieves todos filtered by a specific tag")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved todos by tag")
    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<TodoDTO.Response>> getTodosByTag(
            @Parameter(description = "Tag name", example = "work")
            @PathVariable String tag) {
        log.info("GET /api/todos/tag/{} - Fetching todos by tag", tag);
        List<TodoDTO.Response> todos = todoService.getTodosByTag(tag);
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Get overdue todos", description = "Retrieves all incomplete todos that are past their due date")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved overdue todos")
    @GetMapping("/overdue")
    public ResponseEntity<List<TodoDTO.Response>> getOverdueTodos() {
        log.info("GET /api/todos/overdue - Fetching overdue todos");
        List<TodoDTO.Response> todos = todoService.getOverdueTodos();
        return ResponseEntity.ok(todos);
    }

    @Operation(summary = "Get todo statistics", description = "Retrieves statistics about todos (total, completed, pending, etc.)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved todo statistics")
    @GetMapping("/stats")
    public ResponseEntity<TodoService.TodoStats> getStats() {
        log.info("GET /api/todos/stats - Fetching todo statistics");
        TodoService.TodoStats stats = todoService.getStats();
        return ResponseEntity.ok(stats);
    }
}