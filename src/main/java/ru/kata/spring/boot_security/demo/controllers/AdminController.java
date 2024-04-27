package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "adminPanel";
    }

    @GetMapping("/user_profile/{id}")
    public String showUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", userService.findUserById(id).getRoles());
        return "user_profile";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("userRoles", roleService.findAll());
        System.out.println("Запрос получен на гет method (editUser)");
        return "edit";
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User updUser, @PathVariable("id") Long id) {
        System.out.println("Запрос перенаправлен на patch method");
        userService.updateUser(updUser, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String addNewUser(Model model, User user) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User newUser) {
        userService.saveUser(newUser);
        return "redirect:/admin";
    }
}
