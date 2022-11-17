package net.ripe.orderservice.publisher;

import net.ripe.orderservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.binding.payment.routing.key}")
    private String paymentRoutingKey;

    @Value("${rabbitmq.binding.sms.routing.key}")
    private String smsRoutingKey;

    private RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessagePayment(OrderEvent orderEvent){
        LOGGER.info(String.format("Order event payment sent to RabbitMQ => %s", orderEvent.toString()));

        rabbitTemplate.convertAndSend(exchange, paymentRoutingKey, orderEvent);
    }

    public void sendMessageSms(OrderEvent orderEvent){
        LOGGER.info(String.format("Order event sms sent to RabbitMQ => %s", orderEvent.toString()));

        rabbitTemplate.convertAndSend(exchange, smsRoutingKey, orderEvent);
    }
}
