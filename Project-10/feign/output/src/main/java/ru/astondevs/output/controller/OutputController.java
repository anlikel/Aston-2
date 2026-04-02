package ru.astondevs.output.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.astondevs.output.service.RandomService;
import ru.astondevs.output.web.response.ApiResponse;

@RestController
@RequestMapping("/output")
@RequiredArgsConstructor
public class OutputController {

    private final RandomService randomService;

    @GetMapping("/rest")
    public ResponseEntity<ApiResponse<Integer>> getRandomNumber() {
        int randomNumber = randomService.getRandomNumber();
        return ResponseEntity.ok(ApiResponse.success(randomNumber, null));
    }

}
