package net.ripe.paymentservice.consumer;


import net.ripe.paymentservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumer.class);


    @RabbitListener(queues = "${rabbitmq.queue.payment.name}")
    public void consume(OrderEvent orderEvent) {
        LOGGER.info(String.format("Order event payment received => %s", orderEvent.toString()));

        // save order to database
    }


}
