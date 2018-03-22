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
    public void helloWithRetry() {
        System.out.println(new Date() + " : Calling Hello with retry");
        throw new CoreRtException("Thrown intentionally");
    }

    public void helloWithoutRetry() {
        System.out.println(new Date() + " : Calling Hello without retry");
        throw new CoreRtException("Thrown intentionally");
    }
}
