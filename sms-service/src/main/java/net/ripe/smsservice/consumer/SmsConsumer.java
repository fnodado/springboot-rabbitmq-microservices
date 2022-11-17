package net.ripe.smsservice.consumer;


import net.ripe.smsservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SmsConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsConsumer.class);



    @RabbitListener(queues = "${rabbitmq.queue.sms.name}")
    public void consume(OrderEvent orderEvent){
        LOGGER.info(String.format("Order event received => %s", orderEvent.toString()));

        // save order to database
    }


}
