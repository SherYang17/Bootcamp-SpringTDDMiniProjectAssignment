package com.tdd.tddminiproject.controller;

import com.tdd.tddminiproject.model.Order;
import com.tdd.tddminiproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController{
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{orderId}") //path parameter
    public ResponseEntity<Order> getOrder(@PathVariable String orderId){

        Order myOrder = null;
        Optional<Order> myOrderOpt = orderRepository.findById(Long.valueOf(orderId));
        if (myOrderOpt.isPresent()) {
            myOrder = myOrderOpt.get();
            return ResponseEntity.status(HttpStatus.OK).body(myOrder);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody String request){
        Order newOrder = new Order();

        // Need to change String parameter to a custom 'request object'
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);

//        return null;
    }
}