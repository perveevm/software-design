package ru.perveevm.mvc.model;

import javax.persistence.*;

@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    private Boolean isDone;

    @ManyToOne
    private TodoList inList;

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getDone() {
        return isDone;
    }

    public TodoList getInList() {
        return inList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public void setInLists(TodoList inList) {
        this.inList = inList;
    }
}
