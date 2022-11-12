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

    @RequestMapping("/")
    public String helloPage() {
        return "hello";
    }

    @RequestMapping("/admin/saveUsers")
    public String saveUsers(@ModelAttribute("user") User user) {
        usersService.saveUsers(user);
        return "redirect:/admin";
    }

    @PutMapping("/admin/updateUsers")
    public String updateUsers(@ModelAttribute("user") User user) {
        usersService.updateUsers(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String updateInfo(@ModelAttribute("user") User user) {
        usersService.updateUsers(user);
        return "redirect:/admin";
    }

    @RequestMapping("/admin/deleteUsers/{userId}")
    public String deleteUsers(@PathVariable("userId") int id) {
        usersService.deleteUsers(id);
        return "redirect:/admin";
    }
}
