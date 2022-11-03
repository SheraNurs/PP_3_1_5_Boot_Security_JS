package ru.kata.spring.boot_security.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u join fetch u.roles where u.username = :username")
    Optional<User> findByUsername (@Param("username") String username);
}
