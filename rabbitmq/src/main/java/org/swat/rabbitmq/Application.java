package org.swat.rabbitmq;

import org.apache.commons.logging.Log;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConditionalExceptionLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class Application {

    final static String queueName = "camel-route-"+ UUID.randomUUID().toString();

    @Bean
    Queue queue() {
        return new Queue(queueName, true, true, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        //Provide the configuration here
        connectionFactory.setHost("someIp");
        connectionFactory.setUsername("userName");
        connectionFactory.setPassword("password");
        com.rabbitmq.client.ConnectionFactory rabbitFactory = connectionFactory.getRabbitConnectionFactory();
        rabbitFactory.setAutomaticRecoveryEnabled(true);
        rabbitFactory.setNetworkRecoveryInterval(30000);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        Message message = new Message("".getBytes(), new MessageProperties());
        rabbitTemplate.send("esb-camel-route", message);
        return connectionFactory;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        RetryInterceptorBuilder.StatelessRetryInterceptorBuilder builder = RetryInterceptorBuilder.StatelessRetryInterceptorBuilder.stateless();
        builder.maxAttempts(Integer.MAX_VALUE);
        container.setAdviceChain(builder.build());
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        container.setMissingQueuesFatal(false);
        container.setExclusiveConsumerExceptionLogger(new ConditionalExceptionLogger() {
            @Override
            public void log(Log log, String s, Throwable throwable) {
                System.out.println("Perfect");
            }
        });
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}