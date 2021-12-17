package ru.perveevm.mvc.dao;

import ru.perveevm.mvc.model.Todo;
import ru.perveevm.mvc.model.TodoList;

import java.util.List;

public interface TodoDao {
    void addTodo(Todo todo, long todoListId);

    void removeTodo(long todoId);

    void markDone(long todoId);

    void addTodoList(TodoList todoList);

    void removeTodoList(long todoId);

    List<TodoList> getAllTodoLists();
}
