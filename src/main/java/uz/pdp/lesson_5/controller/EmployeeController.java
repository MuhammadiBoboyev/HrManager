package uz.pdp.lesson_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson_5.entity.User;
import uz.pdp.lesson_5.payload.ApiEmployeeDataResponse;
import uz.pdp.lesson_5.payload.ApiResponse;
import uz.pdp.lesson_5.payload.TaskDto;
import uz.pdp.lesson_5.service.EmployeeService;
import uz.pdp.lesson_5.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public HttpEntity<?> getEmployee() {
        List<User> userList = employeeService.getEmployee();
        return ResponseEntity.status(userList != null ? 200 : 403).body(userList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getEmployee(@PathVariable UUID id) {
        ApiEmployeeDataResponse apiEmployeeDataResponse = employeeService.getEmployeeById(id);
        return ResponseEntity.status(apiEmployeeDataResponse != null ? 200 : 403).body(apiEmployeeDataResponse);
    }
}