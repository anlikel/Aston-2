package ru.astondevs.input.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.input.service.OutputClientService;
import ru.astondevs.input.web.response.ApiResponse;

@RestController
@RequestMapping("/input")
@RequiredArgsConstructor
public class InputController {

    private final OutputClientService outputClientService;

    @GetMapping("/rest")
    public ResponseEntity<ApiResponse<Integer>> getRandomNumber() {
        int randomNumber = outputClientService.getRandomNumberFromOutput();
        return ResponseEntity.ok(ApiResponse.success(randomNumber, null));
    }

    @GetMapping("/feign")
    public ResponseEntity<ApiResponse<Integer>> getRandomNumberFeign() {
        int randomNumber = outputClientService.getRandomNumberFromOutput();
        return ResponseEntity.ok(ApiResponse.success(randomNumber, null));
    }
}
