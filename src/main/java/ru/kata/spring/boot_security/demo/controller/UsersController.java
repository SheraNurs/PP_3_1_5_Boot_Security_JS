package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersService;


@Controller
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping("/admin")
    public String printUsers(Model model) {
        model.addAttribute("allUs", usersService.getAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("user1", usersService.getCurrentUser());
        model.addAttribute("listRoles", usersService.roleList());
        return "AdminPage";
    }

    @RequestMapping("/user")
    public String UserPage(Model model) {
        model.addAttribute("user1", usersService.getCurrentUser());
        return "userPage";
    }
}
