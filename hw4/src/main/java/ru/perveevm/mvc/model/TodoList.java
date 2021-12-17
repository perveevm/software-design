package ru.perveevm.mvc.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "todo_list")
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String name;

    @OneToMany(mappedBy = "inList")
    private List<Todo> items;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Todo> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<Todo> items) {
        this.items = items;
    }
}
