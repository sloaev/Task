package com.resource_processor.rabbitmq;

import com.resource_processor.dto.SongDto;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@EnableRabbit
@Component
public class RabbitMQListener {

    Logger logger = LoggerFactory.getLogger(RabbitMQListener.class);

    @Autowired
    RabbitMQUtils rabbitMQUtils;

    @RabbitListener(queues = "REStoREP")
    public void processMyQueue(String resourceId) {
        logger.info("Receive from REPListener:" + resourceId);
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        ResponseEntity<byte[]> content = rabbitMQUtils.getFromResourceService(resourceId, restTemplate);
        try {
            Metadata metadata = rabbitMQUtils.parse(content.getBody());
            SongDto dto = new SongDto(metadata);
            dto.setResourceId(Integer.valueOf(resourceId));
            rabbitMQUtils.postToSongService(dto,restTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
