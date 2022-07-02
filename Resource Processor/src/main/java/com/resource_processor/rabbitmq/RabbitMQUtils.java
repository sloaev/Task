package com.resource_processor.rabbitmq;

import com.resource_processor.dto.SongDto;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.slf4j.Logger;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class RabbitMQUtils {

    Logger logger = LoggerFactory.getLogger(RabbitMQUtils.class);

    @Retryable(maxAttempts = 10, value = RestClientException.class, backoff = @Backoff(delay = 500, multiplier = 4))
    protected void postToSongService(SongDto dto, RestTemplate restTemplate) {
        logger.info("trying to post to song service...");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8082/soser/postInfo",dto, String.class);
        logger.info("successfully posted");
    }

    @Retryable(maxAttempts = 10, value = Exception.class, backoff = @Backoff(delay = 500, multiplier = 4))
    protected ResponseEntity<byte []> getFromResourceService(String resourceId, RestTemplate restTemplate) {
        logger.info("trying to get from resource service....");
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity("http://localhost:8081/reser/downloadv2?id=" + resourceId, byte[].class);
        logger.info("successfully obtained");
        return responseEntity;
    }

     protected Metadata parse(byte [] content) throws TikaException, IOException, SAXException {
        InputStream input = new ByteArrayInputStream(content);
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        parser.parse(input, handler, metadata, parseCtx);
        input.close();
        return metadata;
    }
}
