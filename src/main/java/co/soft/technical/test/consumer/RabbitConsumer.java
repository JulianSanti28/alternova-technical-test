package co.soft.technical.test.consumer;

import co.soft.technical.test.model.MediaItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {
    private final ObjectMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(RabbitConsumer.class);

    public RabbitConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @RabbitListener(queues = "orders")
    public void receiveOrderMessage(String mediaItemMessage) {
        try {
            MediaItem mediaItem = this.mapper.readValue(mediaItemMessage, MediaItem.class);
            logger.info(mediaItem.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
