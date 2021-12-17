package ru.perveevm.mvc.dao;

import org.springframework.stereotype.Service;
import ru.perveevm.mvc.model.Todo;
import ru.perveevm.mvc.model.TodoList;
import ru.perveevm.mvc.repository.TodoListRepository;
import ru.perveevm.mvc.repository.TodoRepository;

import java.util.List;

@Service
public class TodoJpaDao implements TodoDao {
    private final TodoRepository todoRepository;
    private final TodoListRepository todoListRepository;

    public TodoJpaDao(final TodoRepository todoRepository, final TodoListRepository todoListRepository) {
        this.todoRepository = todoRepository;
        this.todoListRepository = todoListRepository;
    }

    @Override
    public void addTodo(Todo todo, long todoListId) {
        todo.setDone(false);
        todoListRepository.findById(todoListId).ifPresent(todoList -> {
            todo.setInLists(todoList);
            todoRepository.save(todo);
        });
    }

    @Override
    public void removeTodo(long todoId) {
        todoRepository.deleteById(todoId);
    }

    @Override
    public void markDone(long todoId) {
        todoRepository.findById(todoId).ifPresent(todo -> {
            todo.setDone(true);
            todoRepository.save(todo);
        });
    }

    @Override
    public void addTodoList(TodoList todoList) {
        todoListRepository.save(todoList);
    }

    @Override
    public void removeTodoList(long todoListId) {
        todoListRepository.findById(todoListId).ifPresent(todoList -> {
            for (Todo todo : todoList.getItems()) {
                removeTodo(todo.getId());
            }
            todoListRepository.deleteById(todoListId);
        });
    }

    @Override
    public List<TodoList> getAllTodoLists() {
        return todoListRepository.findAll();
    }
}
