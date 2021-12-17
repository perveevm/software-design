package ru.perveevm.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.perveevm.mvc.model.TodoList;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
