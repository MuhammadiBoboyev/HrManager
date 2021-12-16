package uz.pdp.lesson_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson_5.entity.Task;
import uz.pdp.lesson_5.payload.ApiResponse;
import uz.pdp.lesson_5.payload.LoginDto;
import uz.pdp.lesson_5.payload.RegisterDto;
import uz.pdp.lesson_5.payload.TaskDto;
import uz.pdp.lesson_5.service.AuthService;
import uz.pdp.lesson_5.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/byEmployeeId/{id}")
    public HttpEntity<?> getTaskByEmployeeId(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskByEmployeeId(id));
    }

    @PostMapping()
    public HttpEntity<?> task(@RequestBody TaskDto taskDto) {
        ApiResponse apiResponse = taskService.task(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 403).body(apiResponse);
    }

    @PutMapping("/successTask/{id}")
    public HttpEntity<?> success(@PathVariable UUID id) {
        ApiResponse apiResponse = taskService.successTask(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 403).body(apiResponse);
    }
}