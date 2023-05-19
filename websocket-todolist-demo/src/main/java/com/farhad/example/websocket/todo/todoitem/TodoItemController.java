package com.farhad.example.websocket.todo.todoitem;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class TodoItemController {
    
    private final TodoItemService todoItemService;

    @GetMapping 
    public ResponseEntity<List<TodoItem>> all() {
        return ResponseEntity
                        .ok()
                        .body(this.todoItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> byId(@PathVariable("id") Long id) {
        return ResponseEntity.of(this.todoItemService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TodoItem> create(@CookieValue("username") String username, @RequestBody Map<String,String> json ) {

        if(json.get("text") == null) {
            return ResponseEntity.badRequest().body(null);
        }
        TodoItem todoItem = todoItemService.create(json.get("text"), username);
        URI location = ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(todoItem.getId())
                                .toUri();
        return ResponseEntity.created(location).body(todoItem);
    }

    @PutMapping("/{id}/complate")
    public ResponseEntity<TodoItem> complated(@PathVariable("id") Long id) {
        Optional<TodoItem>  maybeUpdated =  this.todoItemService.updateComplationStatus(id, true);

        return maybeUpdated
                    .map(updatedTodo -> ResponseEntity.ok().body(updatedTodo))
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/uncomplate")
    public ResponseEntity<TodoItem> uncomplated(@PathVariable("id") Long id) {
        Optional<TodoItem>  maybeUpdated =  this.todoItemService.updateComplationStatus(id, false);

        return maybeUpdated
                    .map(updatedTodo -> ResponseEntity.ok().body(updatedTodo))
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@CookieValue("username") String username ,@PathVariable("id") Long id) {
        Optional<TodoItem> todo = this.todoItemService.getById(id);

        if(!todo.isPresent()){
            return ResponseEntity.notFound().build();
        }
        if(todo.get().getUserName().equals(username)) {
            this.todoItemService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
}
