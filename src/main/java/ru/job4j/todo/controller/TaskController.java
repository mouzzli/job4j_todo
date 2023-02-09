package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping({"/", "/tasks"})
    public String index(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks";
    }

    @GetMapping("/completedTasks")
    public String completedTask(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(true));
        return "completedTasks";
    }

    @GetMapping("/newTasks")
    public String newTask(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(false));
        return "newTasks";
    }

    @GetMapping("/createTask")
    public String getTaskForm() {
        return "addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.create(task);
        return "redirect:tasks";
    }

    @GetMapping("/task/{id}")
    public String getTask(@PathVariable int id, Model model) {
        try {
            model.addAttribute("task", taskService.findById(id));
            return "task";
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/completeTask")
    public String completeTask(@RequestParam("id") int id, Model model) {
        try {
            taskService.setDone(id);
            return "redirect:task/" + id;
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("id") int id, Model model) {
        try {
            taskService.deleteById(id);
            return "redirect:tasks";
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task, Model model) {
        try {
            taskService.update(task);
            return "redirect:task/" + task.getId();
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/editTask/{id}")
    public String getEditTaskForm(@PathVariable int id, Model model) {
        model.addAttribute("task", taskService.findById(id));
        return "edit";
    }
}
