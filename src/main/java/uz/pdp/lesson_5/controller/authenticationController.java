package uz.pdp.lesson_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson_5.payload.ApiResponse;
import uz.pdp.lesson_5.payload.LoginDto;
import uz.pdp.lesson_5.payload.RegisterDto;
import uz.pdp.lesson_5.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class authenticationController {

    @Autowired
    AuthService authService;

    @GetMapping("/verificationEmail")
    public HttpEntity<?> verificationEmail(@RequestParam String email, @RequestParam String emailCode, @RequestParam String password) {
        ApiResponse apiResponse = authService.verificationEmail(email, emailCode, password);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = authService.loginUser(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 401).body(apiResponse);
    }

    @PostMapping("/registerHrManager")
    public HttpEntity<?> registerHrManager(@RequestBody RegisterDto registerDto) {
        ApiResponse apiResponse = authService.registerEmployee(registerDto, "hr_manager");
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 403).body(apiResponse);
    }

    @PostMapping("/registerEmployee")
    public HttpEntity<?> registerEmployer(@RequestBody RegisterDto registerDto) {
        ApiResponse apiResponse = authService.registerEmployee(registerDto, "employee");
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 403).body(apiResponse);
    }
}
