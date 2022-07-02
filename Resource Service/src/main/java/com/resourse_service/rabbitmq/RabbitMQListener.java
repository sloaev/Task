package com.resourse_service.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitMQListener {

    Logger logger = LoggerFactory.getLogger(RabbitMQListener.class);

    @RabbitListener(queues = "myQueue")
    public void processMyQueue(String message){
        logger.info("Receive from myQueueListener:" + message);
    }
}
