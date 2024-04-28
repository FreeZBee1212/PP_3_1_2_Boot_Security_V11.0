package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin/users")
    public String getUsers(Model model) {
        model.addAttribute("index", userService.getUsers());
        return "admin/users/index";
    }

    @GetMapping("/admin/users/id")
    public String showId(@RequestParam("id") int id, Model model) {
        model.addAttribute("show", userService.showOneUser(id));
        return "admin/users/show";
    }

    @GetMapping("/admin/users/new")
    public String createNewUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/users/new";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
        userService.saveUser(user, roleIds);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/id/edit")
    public String edit(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.showOneUser(id));
        return "admin/users/edit";
    }


    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
        userService.updateUser(user, roleIds);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        User userDelete = userService.showOneUser(id);
        userService.deleteUser(userDelete);
        return "redirect:/admin/users";
    }
}









//@Controller
//@RequestMapping("/users")
//public class UserController {
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//
//    @GetMapping()
//    public String getUsers(Model model) {
//        model.addAttribute("index", userService.getUsers());
//        return "users/index";
//    }
//
//    @GetMapping("/id")
//    public String showId(@RequestParam("id") int id, Model model) {
//        model.addAttribute("show", userService.showOneUser(id));
//        return "users/show";
//    }
//
//    @GetMapping("/new")
//    public String createNewUser(Model model) {
//        model.addAttribute("user", new User());
//        return "users/new";
//    }
//
//    @PostMapping()
//    public String createUser(@ModelAttribute("user") User user) {
//        userService.saveUser(user);
//        return "redirect:/users";
//    }
//
//    @GetMapping("/id/edit")
//    public String edit(Model model, @RequestParam("id") int id) {
//        model.addAttribute("user", userService.showOneUser(id));
//        return "users/edit";
//    }
//
//    @PostMapping("/{id}")
//    public String update(@ModelAttribute("person") User user) {
//        userService.updateUser(user);
//        return "redirect:/users";
//    }
//
//    @PostMapping("/delete")
//    public String delete(@RequestParam("id") int id) {
//        User userDelete = userService.showOneUser(id);
//        userService.deleteUser(userDelete);
//        return "redirect:/users";
//    }
//}
