package org.swat.spring.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

import java.util.Date;

@SpringBootApplication
@EnableRetry
public class SpringRetryMain implements CommandLineRunner {
    @Autowired
    private SpringRetryService springRetryService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringRetryMain.class);
        app.run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        try {
            springRetryService.hello();
        } catch (Exception e) {
            System.out.println(new Date() + " : Exception Message - " + e.getMessage());
        }
        System.exit(0);
    }
}
