package com.chinaka.task.order.producer.mintyn.controller;

import com.chinaka.task.order.producer.mintyn.MintynTaskApplication;
import com.chinaka.task.order.producer.mintyn.dto.OrderDto;
import com.chinaka.task.order.producer.mintyn.dto.ProductDto;
import com.chinaka.task.order.producer.mintyn.util.ProductStatus;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MintynTaskApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getProductShouldBeSuccessful() throws Exception {

        ProductDto productDto = new ProductDto();
        productDto.setName("macbook pro");
        productDto.setDescription("this is a 2023 macbook pro");
        productDto.setCurrencyCode("NAIRA");
        productDto.setQuantity("10");
        productDto.setStatus(ProductStatus.ACTIVE);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/{id}", "5"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.respCode", Matchers.is("00")))
                .andExpect(jsonPath("$.respDescription", Matchers.is("SUCCESS")))
                .andExpect(jsonPath("$.respBody.name", Matchers.is("macbook pro")))
                .andExpect(jsonPath("$.respBody.description", Matchers.is("this is a 2023 macbook pro")))
                .andExpect(jsonPath("$.respBody.price", Matchers.is(350.0)))
                .andExpect(jsonPath("$.respBody.quantity", Matchers.is(10)))
                .andExpect(jsonPath("$.respBody.status", Matchers.is("ACTIVE")))
                .andExpect(jsonPath("$.respBody.currencyCode", Matchers.is("NGN")));
    }


    @Test
    void getProductShouldThrow417() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/{id}", "190"))
                .andExpect(status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }


    @Test
    void lisProductShouldThrowSuccess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.respCode", Matchers.is("00")))
                .andExpect(jsonPath("$.respDescription", Matchers.is("SUCCESS")));
    }

    @Test
    void updateProductShouldBeSuccessful() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setName("macbook pro");
        productDto.setDescription("this is a 2023 macbook pro");
        productDto.setPrice("250");
        productDto.setCurrencyCode("NGN");
        productDto.setQuantity("10");
        productDto.setStatus(ProductStatus.ACTIVE);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.respCode", Matchers.is("00")))
                .andExpect(jsonPath("$.respDescription", Matchers.is("SUCCESS")))

                .andExpect(jsonPath("$.respBody.id", Matchers.is(1)))
                .andExpect(jsonPath("$.respBody.name", Matchers.is("macbook pro")))
                .andExpect(jsonPath("$.respBody.description", Matchers.is("this is a 2023 macbook pro")))
                .andExpect(jsonPath("$.respBody.price", Matchers.is(250)))
                .andExpect(jsonPath("$.respBody.currencyCode", Matchers.is("NGN")))
                .andExpect(jsonPath("$.respBody.quantity", Matchers.is(10)))
                .andExpect(jsonPath("$.respBody.status", Matchers.is("ACTIVE")));
    }

    @Test
    void updateProductShouldThrowBadRequest() throws Exception {

        String json = "{\n" +
                "  \"id\": ,\n" +
                "  \"name\": \"NAIRA\",\n" +
                "  \"description\": \"this is a 2023 macbook pro\",\n" +
                "  \"price\": \"1000\"\n" +
                "}";
        ProductDto productDto = new ProductDto();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProductShouldThrow417() throws Exception {

        String json = "{\n" +
                "  \"amount\": \"1000.00\",\n" +
                "  \"currencyCode\": \"NAIRA\",\n" +
                "  \"date\": \"2022-12-20\",\n" +
                "  \"transactionType\": \"SALE\"\n" +
                "}";
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product/{id}", "100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isExpectationFailed());
    }
}