package ru.astondevs.input.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.astondevs.input.feign.OutputFeignClient;
import ru.astondevs.input.service.OutputClientService;
import ru.astondevs.input.web.response.ApiResponse;

@Service
@RequiredArgsConstructor
public class OutputClientServiceImpl implements OutputClientService {

    private final RestTemplate restTemplate;
    private final OutputFeignClient outputFeignClient;

    @Value("${output.service.url:http://localhost:8180/output/rest}")
    private String outputServiceUrl;

    @Override
    public Integer getRandomNumberFromOutput() {

        ResponseEntity<ApiResponse<Integer>> response = restTemplate.exchange(
            outputServiceUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ApiResponse<Integer>>() {
            }
        );
        return response.getBody().getData();
    }

    @Override
    public Integer getRandomNumberFromOutputFeign() {
        ApiResponse<Integer> response = outputFeignClient.getRandomNumber();
        return response.getData();
    }
}
