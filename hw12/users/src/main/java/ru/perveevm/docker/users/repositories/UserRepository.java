package ru.perveevm.docker.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.perveevm.docker.users.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
