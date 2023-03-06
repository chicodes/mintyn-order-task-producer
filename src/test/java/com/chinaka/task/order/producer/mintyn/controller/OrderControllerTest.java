package com.chinaka.task.order.producer.mintyn.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chinaka.task.order.producer.mintyn.MintynTaskApplication;
import com.chinaka.task.order.producer.mintyn.dto.OrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MintynTaskApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerTest {


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void addOrderShouldBeSuccessful() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setProductId("1");
        orderDto.setQuantity("1");
        orderDto.setPrice("150");
        orderDto.setCustomerName("John Doe");
        orderDto.setCustomerPhone("08033329329");
        orderDto.setCustomerAddress("Victoria Island");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.respCode", Matchers.is("00")))
                .andExpect(jsonPath("$.respDescription", Matchers.is("SUCCESS")))

                .andExpect(jsonPath("$.respBody.productId", Matchers.is("1")))
                .andExpect(jsonPath("$.respBody.customerName", Matchers.is("John Doe")))
                .andExpect(jsonPath("$.respBody.customerPhone", Matchers.is("08033329329")))
                .andExpect(jsonPath("$.respBody.customerAddress", Matchers.is("Victoria Island")))
                .andExpect(jsonPath("$.respBody.price", Matchers.is("150")));
    }


    @Test
    void addOrderShouldThrowBadRequest() throws Exception {

        String json = "{\n" +
                "  \"productId\": ,\n" +
                "  \"customerName\": \"john doe\",\n" +
                "  \"customerPhone\": \"12/20/2021\",\n" +
                "  \"customerAddress\": \"Ajah\"\n" +
                "}";
        OrderDto orderDto = new OrderDto();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
