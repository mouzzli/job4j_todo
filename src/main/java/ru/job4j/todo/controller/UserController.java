package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/login")
    public String getLoginForm() {
        return "user/login";
    }

    @GetMapping("/register")
    public String getRegisterForm() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user, HttpServletRequest request) {
        var optionalUser = userService.save(user);
        if (optionalUser.isPresent()) {
            var session = request.getSession();
            session.setAttribute("user", optionalUser.get());
            return "redirect:/tasks";
        }
        model.addAttribute("message", "Пользователь с таким логином уже существует");
        return "error/error";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute User user, HttpServletRequest request) {
        var optionalUser = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (optionalUser.isPresent()) {
            var session = request.getSession();
            session.setAttribute("user", optionalUser.get());
            return "redirect:/tasks";
        }
        model.addAttribute("error", "Пользователь с таким логином или паролем не найден!");
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "user/login";
    }
}
