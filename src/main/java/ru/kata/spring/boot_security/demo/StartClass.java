//package ru.kata.spring.boot_security.demo;
//
//import org.springframework.stereotype.Component;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//
//import javax.annotation.PostConstruct;
//import java.util.Collections;
//import java.util.HashSet;
//
//@Component
//public class StartClass {
//
//    @PostConstruct
//    public void start() {
//        User firstUser = new User("admin", "userSurname", "Moscow", "test");
//        User secondUser = new User("user", "userSurname", "Moscow", "test");
//
//        Role roleAdmin = new Role("ROLE_ADMIN");
//        Role roleUser = new Role("ROLE_USER");
//        firstUser.setRoles(new HashSet<>(Collections.singleton(roleAdmin)));
//        secondUser.setRoles(new HashSet<>(Collections.singleton(roleUser)));
//    }
//}
