package ru.astondevs.input.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.astondevs.input.feign.configuration.FeignRetryConfig;
import ru.astondevs.input.web.response.ApiResponse;

@FeignClient(name = "output-client",
    url = "${output.service.url:http://localhost:8180}",
    configuration = FeignRetryConfig.class
)
public interface OutputFeignClient {

    @GetMapping("/output/rest")
    ApiResponse<Integer> getRandomNumber();
}