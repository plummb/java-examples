package org.swat.spring.retry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.swat.core.utils.CoreRtException;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class SpringBeans {
    @Bean("retryInterceptor")
    public RetryOperationsInterceptor retryInterceptor() {
        RetryOperationsInterceptor interceptor = new RetryOperationsInterceptor();
        RetryTemplate retryTemplate = new RetryTemplate();

        Map<Class<? extends Throwable>, Boolean> exceptionMap = new LinkedHashMap<>();
        exceptionMap.put(CoreRtException.class, true);
        exceptionMap.put(RuntimeException.class, true);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5, exceptionMap, true);
        retryTemplate.setRetryPolicy(retryPolicy);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2);
        backOffPolicy.setMaxInterval(5000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        interceptor.setRetryOperations(retryTemplate);
        return interceptor;
    }
}
