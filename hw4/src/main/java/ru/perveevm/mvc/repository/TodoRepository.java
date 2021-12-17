package ru.perveevm.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.perveevm.mvc.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
