package com.manindra.Order_Service.controller;

import com.manindra.Base_Domains.dto.Order;
import com.manindra.Base_Domains.dto.OrderEvent;
import com.manindra.Order_Service.kafka.OrderProducer;
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

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){

        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent=new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order is Placed and Status is pending");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);

        return "Order Placed Successfully...";
    }
}
