package com.tdd.tddminiproject.controller;

import com.tdd.tddminiproject.model.Order;
import com.tdd.tddminiproject.repository.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;


import java.util.ArrayList;
import java.util.List;
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

    // part 6
    @PutMapping("/{orderId}")
    public ResponseEntity<Object> updateOrder(@PathVariable String orderId, @Valid @RequestBody Order updatedOrder) {
        Optional<Order> existingOrderOpt = orderRepository.findById(Long.valueOf(orderId));
        if (existingOrderOpt.isEmpty()) {
            throw new RuntimeException("Order with ID " + orderId + " not found.");
        }

        // Update the existing order with the new data
        Order existingOrder = existingOrderOpt.get();
        existingOrder.setCustomerName(updatedOrder.getCustomerName());
        existingOrder.setOrderDate(updatedOrder.getOrderDate());
        existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
        existingOrder.setTotal(updatedOrder.getTotal());

        // Save the updated order
        Order savedOrder = orderRepository.save(existingOrder);

        return ResponseEntity.ok(savedOrder);
    }


    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Retrieve all validation errors
            List<FieldError> errors = bindingResult.getFieldErrors();

            // Create a list of error messages
            List<String> errorMessages = new ArrayList<>();
            for (FieldError error : errors) {
                errorMessages.add(error.getDefaultMessage());
            }

            // Return a bad request response with the list of error messages
            return ResponseEntity.badRequest().body(errorMessages);
        }

        // Perform the logic to save the order to the database
        Order savedOrder = orderRepository.save(order);

        // Return the saved order in the response with a success status
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }








    // Code for part 4
//    @PostMapping("/create")
//    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            // Handle validation errors and return a bad request response
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // Perform the logic to save the order to the database
//        Order savedOrder = orderRepository.save(order);
//
//        // Return the saved order in the response with a success status
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
//    }




    // Part 5 I had to remove this below. It doens't work after part 5...
//    @PostMapping("/create")
//    public ResponseEntity<Order> createOrder(@RequestBody String request){
//        Order newOrder = new Order();
//
//        // Need to change String parameter to a custom 'request object'
//        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
//
////        return null;
//    }

    // Code for rest api part 5
//    @PostMapping("/create")
//    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            String errorMessage = bindingResult.getFieldErrors()
//                    .stream()
//                    .map(FieldError::getDefaultMessage)
//                    .findFirst()
//                    .orElseGet(() -> new FieldError("", "", "Invalid order data.").getDefaultMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).body(order);
//        // Code to save the order and return the response
//    }

}