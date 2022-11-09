package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.security.UsersServiceDet;
import ru.kata.spring.boot_security.demo.service.UsersService;


import java.util.Collections;
import java.util.List;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final RoleRepository roleRepository;

    public UsersController(UsersService usersService, RoleRepository roleRepository) {
        this.usersService = usersService;
        this.roleRepository = roleRepository;
    }

    @RequestMapping("/admin")
    public String printUsers(Model model) {
        List<User> allUsers = usersService.getAllUsers();
        model.addAttribute("allUs", allUsers);
        User user = new User();
        model.addAttribute("user", user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersServiceDet usersServiceDet = (UsersServiceDet) authentication.getPrincipal();
        User user1 = usersServiceDet.getUser();
        model.addAttribute("user1", user1);
        model.addAttribute("listRoles", usersService.roleList());
        return "AdminPage";
    }

    @RequestMapping("/user")
    public String UserPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersServiceDet usersServiceDet = (UsersServiceDet) authentication.getPrincipal();
        User user = usersServiceDet.getUser();
        model.addAttribute("user1", user);
        return "userPage";
    }

    @RequestMapping("/")
    public String helloPage() {
        return "hello";
    }

//    @RequestMapping("/admin/addNewUsers")
//    public String addNewUsers(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//        return "infoUsers";
//    }

    @RequestMapping("/admin/saveUsers")
    public String saveUsers(@ModelAttribute("user") User user) {
        Role role;
        if (user.getRoles() == null) {
            usersService.addRole(role = new Role("ROLE_USER"));
            user.setRoles(Collections.singletonList(role));
            usersService.saveUsers(user);
        }
        usersService.saveUsers(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String updateUserGet(@PathVariable("id") int id, Model model) {
        User user = usersService.getUsers(id);
        model.addAttribute("user", user);
        return "/admin/edit";
    }

    @PostMapping("/admin/edit")
    public String updateInfo(@ModelAttribute("user") User user) {
        usersService.saveUsers(user);
        return "redirect:/admin";
    }

    @RequestMapping("/admin/deleteUsers")
    public String deleteUsers(@RequestParam("userId") int id) {
        usersService.deleteUsers(id);
        return "redirect:/admin";
    }

}
