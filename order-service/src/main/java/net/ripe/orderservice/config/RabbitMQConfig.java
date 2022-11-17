package net.ripe.orderservice.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //queue for payment microservice
    @Value("${rabbitmq.queue.payment.name}")
    private String paymentQueue;

    //queue for sms microservice
    @Value("${rabbitmq.queue.sms.name}")
    private String smsQueue;

    @Value("${rabbitmq.exchange.name}")
    private String orderExchange;

    @Value("${rabbitmq.binding.payment.routing.key}")
    private String paymentKey;

    @Value("${rabbitmq.binding.sms.routing.key}")
    private String smsKey;

    // spring bean for queue - order queue
    @Bean
    public Queue paymentQueue() {
        return new Queue(paymentQueue);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(smsQueue);
    }

    // spring bean for exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(orderExchange);
    }
    // spring bean for binding between exchange and queue using routing key
    @Bean
    public Binding paymentBinding(){
        return BindingBuilder
                .bind(paymentQueue())
                .to(exchange())
                .with(paymentKey);
    }

    @Bean
    public Binding smsBinding(){
        return BindingBuilder
                .bind(smsQueue())
                .to(exchange())
                .with(smsKey);
    }
    // message converter
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    //configure RabbitTemplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }


}
