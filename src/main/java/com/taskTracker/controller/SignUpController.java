package com.taskTracker.controller;

import com.taskTracker.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.taskTracker.service.impl.RegisterServiceImpl;

@Controller
@RequiredArgsConstructor
public class SignUpController {
    private final RegisterServiceImpl registerServiceImpl;

    @GetMapping("/sign-up")
    public String getRegistrationPage(Model model) {

        RegisterDto registerDto = new RegisterDto();
        model.addAttribute("registerRequest", registerDto);

        return "registration";
    }

    @PostMapping("/sign-up")
    public String addUser(RegisterDto registerDto, Model model) {

        try {
            registerServiceImpl.register(registerDto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("fieldsNotFilledMsg", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("registrationErrorMsg", e.getMessage());
        }

        return "redirect:/sign-in";
    }
}
