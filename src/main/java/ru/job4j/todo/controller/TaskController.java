package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "task/tasks";
    }

    @GetMapping("/completed")
    public String completedTask(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(true));
        return "task/completed";
    }

    @GetMapping("/new")
    public String newTask(Model model) {
        model.addAttribute("tasks", taskService.findByStatus(false));
        return "task/new";
    }

    @GetMapping("/create")
    public String getTaskForm() {
        return "task/add";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) {
        taskService.create(task);
        return "redirect:/tasks";
    }

    @GetMapping("/task/{id}")
    public String getTask(@PathVariable int id, Model model) {
        var optionalTask = taskService.findById(id);
        if (optionalTask.isPresent()) {
            model.addAttribute("task", optionalTask.get());
            return "task/task";
        }
        model.addAttribute("message", String.format("Задачи с id = %s не сушествует", id));
        return "error/error";
    }

    @PostMapping("/complete")
    public String completeTask(@RequestParam("id") int id, Model model) {
        var isDone = taskService.setDone(id);
        if (!isDone) {
            model.addAttribute("message", String.format("Невозможно завершить задачу. id = %s не сушествует", id));
            return "error/error";
        }

        return "redirect:task/" + id;
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam("id") int id, Model model) {
        var isDelete = taskService.deleteById(id);
        if (!isDelete) {
            model.addAttribute("message", String.format("Невозможно удалить задачу. id = %s не существует", id));
            return "error/error";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task, Model model) {
        var isUpdate = taskService.update(task);
        if (!isUpdate) {
            model.addAttribute("message", String.format("Невозможно обновить задачу. id = %s не существует", task.getId()));
            return "error/error";
        }
        return "redirect:task/" + task.getId();
    }

    @GetMapping("/edit/{id}")
    public String getEditTaskForm(@PathVariable int id, Model model) {
        var optionalTask = taskService.findById(id);
        if (optionalTask.isPresent()) {
            model.addAttribute("task", optionalTask.get());
            return "task/edit";
        }
        model.addAttribute("message", String.format("Задачи с id = %s не сушествует", id));
        return "error/error";
    }
}
