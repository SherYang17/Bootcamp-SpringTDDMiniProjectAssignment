package com.tdd.tddminiproject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Part 4
    @Test
    public void createOrder_Successful() throws Exception {
        //Returns a 201 status which is success and body has the info
        String request = "{\"customerName\": \"John Doe\", \"orderDate\": \"2023-07-06\", \"shippingAddress\": \"123 Main St\", \"total\": 100.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderDate").value("2023-07-06"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shippingAddress").value("123 Main St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(100.0))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createOrder_Failure() throws Exception {
        // Status = 400 which is a failure, the body states must not be empty so it is a pass.
        String request = "{\"customerName\": \"\", \"orderDate\": null, \"shippingAddress\": \"\", \"total\": -10.0}";

        mockMvc.perform(MockMvcRequestBuilders.post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    // Part 5
    @Test
    public void createOrder_ValidationErrors_ShouldReturnBadRequest() throws Exception {
        // Status shows = 400 which is bad request
        // Body will say Body = ["must not be empty","must not be empty","must be greater than 0","must not be null"]
        String request = "{ \"customerName\": \"\", \"orderDate\": null, \"shippingAddress\": \"\", \"total\": -10.0 }";

        mockMvc.perform(MockMvcRequestBuilders.post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }


    // part 6
    @Test
    // Body will Body = Order with ID 999 not found.
    public void updateOrder_OrderNotFound() throws Exception {
        String orderId = "999"; // Assuming order with ID 999 does not exist
        String request = "{\"customerName\": \"John Doe\", \"orderDate\": \"2023-07-06\", \"shippingAddress\": \"123 Main St\", \"total\": 100.0}";

        mockMvc.perform(MockMvcRequestBuilders.put("/order/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }


}

