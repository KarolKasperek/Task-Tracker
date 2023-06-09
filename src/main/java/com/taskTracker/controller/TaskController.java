package com.taskTracker.controller;

import com.taskTracker.dto.TaskRequest;
import com.taskTracker.service.RegisterService;
import com.taskTracker.service.TaskService;
import com.taskTracker.exception.TaskDoesNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class TaskController {
    private TaskService taskService;
    private RegisterService registerService;

    @GetMapping("/task-details")
    public String getTask(@RequestParam(required = false) String status, Model model) {

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setStatus(status);
        model.addAttribute("taskRequest", taskRequest);
        model.addAttribute("accounts", registerService.getAllAccounts());
        return "task";
    }

    @GetMapping("/edit/{id}")
    public String getTaskDetails(@PathVariable Long id, Model model) {

        addUsersAttribute(model);
        model.addAttribute("accounts", registerService.getAllAccounts());
        try {
            model.addAttribute("taskRequest", taskService.getTaskInfo(id));
        } catch (TaskDoesNotExistException e) {
            model.addAttribute("noTaskMessage", e.getMessage());
        }

        return "task";
    }

    @PostMapping("/task-details")
    public String addTask(TaskRequest taskRequest, Model model) {

        taskService.addTask(taskRequest);
        return "redirect:/";
    }

    private void addUsersAttribute(Model model) {
        model.addAttribute("users", registerService.getAllAccounts());
    }
}
