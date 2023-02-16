package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;

    @GetMapping
    public String index(Model model, @SessionAttribute User user) {
        model.addAttribute("tasks", taskService.findAll(user.getId()));
        return "task/tasks";
    }

    @GetMapping("/completed")
    public String completedTask(Model model, @SessionAttribute User user) {
        model.addAttribute("tasks", taskService.findByStatus(true, user.getId()));
        return "task/completed";
    }

    @GetMapping("/new")
    public String newTask(Model model, @SessionAttribute User user) {
        model.addAttribute("tasks", taskService.findByStatus(false, user.getId()));
        return "task/new";
    }

    @GetMapping("/create")
    public String getTaskForm(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        return "task/add";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, @SessionAttribute User user) {
        task.setUser(user);
        taskService.create(task);
        return "redirect:/tasks";
    }

    @GetMapping("/task/{id}")
    public String getTask(@PathVariable int id, Model model, @SessionAttribute User user) {
        var optionalTask = taskService.findById(id, user.getId());
        if (optionalTask.isPresent()) {
            model.addAttribute("task", optionalTask.get());
            return "task/task";
        }
        model.addAttribute("message", String.format("Задача с id = %s не сушествует или принадлежит другому пользователю", id));
        return "error/error";
    }

    @PostMapping("/complete")
    public String completeTask(@RequestParam("id") int id, Model model, @SessionAttribute User user) {
        var isDone = taskService.setDone(id, user.getId());
        if (!isDone) {
            model.addAttribute("message", String.format("Невозможно завершить задачу. id = %s не сушествует или принадлежит другому пользователю", id));
            return "error/error";
        }

        return "redirect:task/" + id;
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam("id") int id, Model model, @SessionAttribute User user) {
        var isDelete = taskService.deleteById(id, user.getId());
        if (!isDelete) {
            model.addAttribute("message", String.format("Невозможно удалить задачу. id = %s не существует или принадлежит другому пользователю", id));
            return "error/error";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task, Model model, @SessionAttribute User user) {
        var isUpdate = taskService.update(task, user.getId());
        if (!isUpdate) {
            model.addAttribute("message", String.format("Невозможно обновить задачу. id = %s не существует или принадлежит другому пользователю", task.getId()));
            return "error/error";
        }
        return "redirect:task/" + task.getId();
    }

    @GetMapping("/edit/{id}")
    public String getEditTaskForm(@PathVariable int id, Model model, @SessionAttribute User user) {
        var optionalTask = taskService.findById(id, user.getId());
        if (optionalTask.isPresent()) {
            model.addAttribute("priorities", priorityService.findAll());
            model.addAttribute("task", optionalTask.get());
            return "task/edit";
        }
        model.addAttribute("message", String.format("Задача с id = %s не сушествует или принадлежит другому пользовтелю ", id));
        return "error/error";
    }

}
