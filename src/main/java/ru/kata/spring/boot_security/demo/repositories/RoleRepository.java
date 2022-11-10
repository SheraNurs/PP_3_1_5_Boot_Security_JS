package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("select r from Role r where r.name = :name")
    Role getRoleByName (String name);
}
