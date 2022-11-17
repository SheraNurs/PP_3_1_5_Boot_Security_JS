package ru.kata.spring.boot_security.demo.security;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UsersService;

import java.util.Collections;


@Component
public class StartClass implements ApplicationRunner {
    private final UsersService usersService;
    private final RoleRepository roleRepository;

    public StartClass(UsersService usersService, RoleRepository roleRepository) {
        this.usersService = usersService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User admin = new User( "admin", "admin", 26, "admin@mail", "test");
        User user = new User( "user", "user", 22, "user@mail", "test");
        Role adminRole;
        Role userRole;
        roleRepository.save(adminRole = new Role("ROLE_ADMIN"));
        roleRepository.save(userRole = new Role("ROLE_USER"));
        admin.setRoles(Collections.singletonList(adminRole));
        user.setRoles(Collections.singletonList(userRole));
        usersService.saveUsers(admin);
        usersService.saveUsers(user);
    }
}

