package uz.pdp.lesson_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson_5.entity.Salary;
import uz.pdp.lesson_5.payload.ApiResponse;
import uz.pdp.lesson_5.payload.SalaryDto;
import uz.pdp.lesson_5.payload.TaskDto;
import uz.pdp.lesson_5.service.EmployeeService;
import uz.pdp.lesson_5.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/{id}")
    public HttpEntity<?> getSalaryBuEmployeeId(@PathVariable UUID id) {
        List<Salary> salaryByEmployeeId = employeeService.getSalaryByEmployeeId(id);
        return ResponseEntity.ok(salaryByEmployeeId);
    }

    @PutMapping
    public HttpEntity<?> success(@RequestBody SalaryDto salaryDto) {
        ApiResponse apiResponse = employeeService.salary(salaryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 403).body(apiResponse);
    }
}