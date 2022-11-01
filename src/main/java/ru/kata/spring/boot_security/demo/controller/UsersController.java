package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UsersServiceDet;
import ru.kata.spring.boot_security.demo.service.UsersService;


import java.util.List;

@Controller
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping("/admin")
    public String printUsers(Model model) {

        List<User> allUsers = usersService.getAllUsers();
        model.addAttribute("allUs", allUsers);
        return "users";
    }

    @RequestMapping("/user")
    public String UserPage (Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersServiceDet usersServiceDet = (UsersServiceDet) authentication.getPrincipal();
        User user = usersServiceDet.getUser();
        model.addAttribute("user1", user);
        return "user";
    }

    @RequestMapping("/")
    public String helloPage () {
        return "hello";
    }

    @RequestMapping ("/admin/addNewUsers")
    public String addNewUsers(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "users_info";
    }

    @RequestMapping("/admin/saveUsers")
    public String saveUsers(@ModelAttribute("user") User user) {
        usersService.saveUsers(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String updateUserGet(@PathVariable("id") int id, Model model) {
        User user = usersService.getUsers(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/admin/edit")
    public String updateInfo(@ModelAttribute("user") User user){
        usersService.saveUsers(user);
        return "redirect:/admin";
    }

    @RequestMapping("/admin/deleteUsers")
    public String deleteUsers(@RequestParam("userId") int id) {
        usersService.deleteUsers(id);
        return "redirect:/admin";
    }

}
