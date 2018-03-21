package org.swat.spring.retry;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.swat.core.utils.CoreRtException;

import java.util.Random;

@Service
public class SpringRetryService {
    private static final Random RANDOM = new Random(System.nanoTime());

    @Retryable(value = CoreRtException.class)
    public void hello() {
        System.out.println("Calling Hello");
        int x = RANDOM.nextInt(100);
        if (x <= 50)
            throw new CoreRtException("Thrown intentionally");
        System.out.println("Hello successful");
    }
}
