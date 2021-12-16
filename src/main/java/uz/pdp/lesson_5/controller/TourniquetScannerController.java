package uz.pdp.lesson_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson_5.payload.ApiResponse;
import uz.pdp.lesson_5.payload.TaskDto;
import uz.pdp.lesson_5.payload.TourniquetScannerDto;
import uz.pdp.lesson_5.service.TaskService;
import uz.pdp.lesson_5.service.TourniquetScannerService;

import java.util.UUID;

@RestController
@RequestMapping("/api/scanner")
public class TourniquetScannerController {

    @Autowired
    TourniquetScannerService tourniquetScannerService;

    @PutMapping()
    public HttpEntity<?> success(@RequestBody TourniquetScannerDto tourniquetScannerDto) {
        ApiResponse apiResponse = tourniquetScannerService.tourniquetScanner(tourniquetScannerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 403).body(apiResponse);
    }
}