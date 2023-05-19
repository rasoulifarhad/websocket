package com.farhad.example.websocket.todo.todoitem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TodoItem {
    
    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String text;

    @NonNull
    private String userName;

    private boolean complated = false; 
}
