package com.tdd.tddminiproject.repository;

import com.tdd.tddminiproject.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest //this sets up spring app context with a database to test jpa applications
public class OrderRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder(){
        Order orders = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);
        Order savedOrder = orderRepository.save(orders);

        Order existingOrder = entityManager.find(Order.class, savedOrder.getId());
        Assertions.assertNotNull(existingOrder);
        Assertions.assertEquals(existingOrder.getCustomerName(), orders.getCustomerName());
        Assertions.assertEquals(existingOrder.getShippingAddress(), orders.getShippingAddress());
        Assertions.assertEquals(existingOrder.getTotal(), orders.getTotal());
    }

    @Test
    public void testPass() {
        // Create an Order object
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);

        // Save the Order object using the repository
        Order savedOrder = orderRepository.save(order);

        // Verify that the Order object is saved and has a valid ID
        Assertions.assertNotNull(savedOrder.getId());
    }

    @Test
    public void testFail() {
        // Create an Order object
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);

        // Save the Order object using the repository
        Order savedOrder = orderRepository.save(order);

        // Verify that the Order object is saved and has a valid ID
        Assertions.assertNull(savedOrder.getId()); // Actual = 1 meaning id1 was created but is not seen due to assertnull. It passes even tho it looks like it failed
    }

    @Test
    public void testDeleteNonExistingOrder() {
        // Create an Order object with a non-existing ID
        Order order = new Order();
        order.setId(999L);

        // Attempt to delete the Order using the repository
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> orderRepository.delete(order));
    }

    @Test
    public void testDeleteOrder() {
        // Create an Order object
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);
        Order savedOrder = entityManager.persistAndFlush(order);

        // Delete the Order using the repository
        orderRepository.deleteById(savedOrder.getId());

        // Verify that the Order is no longer present in the database
        boolean orderExists = orderRepository.existsById(savedOrder.getId());
        Assertions.assertFalse(orderExists);
    }

    @Test
    public void testUpdateOrder() {
        // Create an Order object and save it to the database
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);
        Order savedOrder = entityManager.persistAndFlush(order);

        // Modify the Order object
        savedOrder.setCustomerName("Jane Smith");
        savedOrder.setTotal(300.0);

        // Update the Order using the repository
        Order updatedOrder = orderRepository.save(savedOrder);

        // Verify that the Order is updated with the new values
        Assertions.assertEquals(savedOrder.getCustomerName(), updatedOrder.getCustomerName());
        Assertions.assertEquals(savedOrder.getTotal(), updatedOrder.getTotal());
    }

    @Test
    public void testReadOrder() {
        // Create an Order object and save it to the database
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);
        Order savedOrder = entityManager.persistAndFlush(order);

        // Retrieve the Order from the database using its ID
        Optional<Order> retrievedOrder = orderRepository.findById(savedOrder.getId());

        // Verify that the retrieved Order matches the original Order
        Assertions.assertTrue(retrievedOrder.isPresent());
        Assertions.assertEquals(savedOrder.getId(), retrievedOrder.get().getId());
        Assertions.assertEquals(savedOrder.getCustomerName(), retrievedOrder.get().getCustomerName());
        Assertions.assertEquals(savedOrder.getShippingAddress(), retrievedOrder.get().getShippingAddress());
        Assertions.assertEquals(savedOrder.getTotal(), retrievedOrder.get().getTotal());
    }

    @Test
    public void testCreateOrder() {
        // Create an Order object
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);

        // Save the Order using the repository
        Order savedOrder = orderRepository.save(order);

        // Verify that the Order is saved with a non-null ID
        Assertions.assertNotNull(savedOrder.getId());
    }


}


















//package com.tdd.tddminiproject.repository;
//
//import com.tdd.tddminiproject.model.Order;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@DataJpaTest
//public class OrderRepositoryTests {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//
////    @Test
////    public void testSaveOrder() {
//        // Create a new Order object
////        Order order = new Order("John Doe", LocalDate.now(), "123 Main St", 100.0);
////        orderRepository.save(order);
//        // Save the order to the repository
////        Order savedOrder = orderRepository.save(order);
//
//        // Verify that the order is saved and assigned an id
////        assertNotNull(savedOrder.getId());
////        assertEquals(order.getCustomerName(), savedOrder.getCustomerName());
////        assertEquals(order.getOrderDate(), savedOrder.getOrderDate());
////        assertEquals(order.getShippingAddress(), savedOrder.getShippingAddress());
////        assertEquals(order.getTotal(), savedOrder.getTotal());
//    }
