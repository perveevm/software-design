package ru.perveevm.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.perveevm.mvc.dao.TodoDao;
import ru.perveevm.mvc.model.Todo;
import ru.perveevm.mvc.model.TodoList;

import java.util.List;

@Controller
public class TodoController {
    private final TodoDao todoDao;

    public TodoController(final TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    @PostMapping("/add-todo")
    public String addTodo(@ModelAttribute("todo") Todo todo, @ModelAttribute("todoListId") long todoListId) {
        todoDao.addTodo(todo, todoListId);
        return "redirect:todo-list";
    }

    @PostMapping("/remove-todo")
    public String removeTodo(@ModelAttribute("todoId") long todoId) {
        todoDao.removeTodo(todoId);
        return "redirect:todo-list";
    }

    @PostMapping("/mark-done")
    public String markDone(@ModelAttribute("todoId") long todoId) {
        todoDao.markDone(todoId);
        return "redirect:todo-list";
    }

    @PostMapping("/add-todo-list")
    public String addTodoList(@ModelAttribute("todoList") TodoList todoList) {
        todoDao.addTodoList(todoList);
        return "redirect:todo-list";
    }

    @PostMapping("/remove-todo-list")
    public String removeTodoList(@ModelAttribute("todoListId") long todoListId) {
        todoDao.removeTodoList(todoListId);
        return "redirect:todo-list";
    }

    @GetMapping("/todo-list")
    public String todoList(Model model) {
        List<TodoList> todoLists = todoDao.getAllTodoLists();
        model.addAttribute("todoLists", todoLists);
        model.addAttribute("todoList", new TodoList());
        model.addAttribute("todo", new Todo());
        return "index";
    }
}
