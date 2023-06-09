package com.taskTracker.controller;

import com.taskTracker.dto.TaskRequest;
import com.taskTracker.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.DateTimeException;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {
    private TaskService taskService;

    @GetMapping("/")
    public String getHome(Model model) {
        List<TaskRequest> taskList = taskService.getAllTasks();

        model.addAttribute("taskList", taskList);
        getTaskRequest(model);

        return "index";
    }

    public String getTaskRequest(Model model) {
        TaskRequest taskRequest = new TaskRequest();

        model.addAttribute("taskRequest", taskRequest);

        return "index";
    }

    @PostMapping
    public String processAddingTask(@Valid @ModelAttribute("taskRequest") TaskRequest taskRequest, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "index";
        } else {

            try {
                taskService.addTask(taskRequest);
            } catch (IllegalArgumentException e) {
                model.addAttribute("fieldsNotFilledMsg", e.getMessage());
                return "index";
            } catch (DateTimeException d) {
                model.addAttribute("invalidDateMsg", d.getMessage());
                return "index";
            }

            return "redirect:/";
        }
    }
}
