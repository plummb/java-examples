package org.swat.spring.retry;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.swat.core.utils.CoreRtException;

import java.util.Date;
import java.util.Random;

@Service
public class SpringRetryService {
    private static final Random RANDOM = new Random(System.nanoTime());

    @Retryable(interceptor = "retryInterceptor")
    public void hello() {
        System.out.println(new Date() + " : Calling Hello");
        int x = RANDOM.nextInt(100);
        if (x <= 100) {
            throw new CoreRtException("Thrown intentionally");
        }
        System.out.println("Hello successful");
    }
}
