package net.ripe.orderservice.controller;

import net.ripe.orderservice.dto.Order;
import net.ripe.orderservice.dto.OrderEvent;
import net.ripe.orderservice.publisher.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders/payment")
    public String placeOrderPayment(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("Pending payment");
        orderEvent.setMessage("Order is in pending payment status");
        orderEvent.setOrder(order);

        orderProducer.sendMessagePayment(orderEvent);

        return "Order sent to the RabbitMQ...";
    }

    @PostMapping("/orders/sms")
    public String placeOrderSms(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("Pending Sms");
        orderEvent.setMessage("Order is in pending Sms status");
        orderEvent.setOrder(order);

        orderProducer.sendMessageSms(orderEvent);

        return "Order sent to the RabbitMQ...";
    }
}
