package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.security.UsersServiceDet;

import java.util.*;

@Service
public class UsersService implements UserDetailsService {


    private final UsersRepository usersRepository;

    private final RoleRepository roleRepository;

    public UsersService(UsersRepository usersRepository,RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public void saveUsers(User user) {
        usersRepository.save(user);
    }

    public void updateUsers(User user) {
        usersRepository.save(user);
    }

    public User getUsers(int id) {
        ru.kata.spring.boot_security.demo.model.User user = null;
        Optional<User> optional = usersRepository.findById(id);
        if (optional.isPresent())
            user = optional.get();
        return user;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersServiceDet usersServiceDet = (UsersServiceDet) authentication.getPrincipal();
        return usersServiceDet.getUser();
    }

    public void deleteUsers(int id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = usersRepository.findByUsername(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new UsersServiceDet(optional.get());
    }

    public List<Role> roleList() {
        return roleRepository.findAll();
    }
}
