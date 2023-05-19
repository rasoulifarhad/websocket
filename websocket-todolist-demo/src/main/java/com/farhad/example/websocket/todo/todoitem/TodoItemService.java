package com.farhad.example.websocket.todo.todoitem;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoItemService {
    
    private final TodoItemRepository todoItemRepository;

    public List<TodoItem> findAll() {
        return this.todoItemRepository.findAll();
    }

    public Optional<TodoItem> getById(Long id) {
        return this.todoItemRepository.findById(id);
    }

    public TodoItem create(String text, String userName) {
        TodoItem todoitem = new TodoItem(text, userName);
        return this.todoItemRepository.save(todoitem);
    }

    public void deleteById(Long id) {
        this.todoItemRepository.deleteById(id);
    }

    public Optional<TodoItem> updateComplationStatus(Long id, boolean status) {
        return this.todoItemRepository
                        .findById(id)
                        .map(oldTodo -> {
                            oldTodo.setComplated(status);
                            return this.todoItemRepository.save(oldTodo);
                        });
    }
}
