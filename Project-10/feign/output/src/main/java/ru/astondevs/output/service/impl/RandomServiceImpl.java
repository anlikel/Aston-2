package ru.astondevs.output.service.impl;

import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.astondevs.output.service.RandomService;

@AllArgsConstructor
@Service
public class RandomServiceImpl implements RandomService {

    private final Random rand;

    @Override
    public int getRandomNumber() {
        return rand.nextInt(10000);
    }
}
