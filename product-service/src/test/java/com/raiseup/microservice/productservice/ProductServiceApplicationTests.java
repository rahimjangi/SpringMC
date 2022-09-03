package com.raiseup.microservice.productservice;

import com.raiseup.microservice.productservice.dto.ProductRequest;
import com.raiseup.microservice.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProductServiceApplicationTests {
    @Container
    static MySQLContainer mySQLContainer= new MySQLContainer("mysql:latest");

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.datasource.url",mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username",mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password",mySQLContainer::getPassword);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ObjectMapper objectMapper= new ObjectMapper();
        ProductRequest productRequest = getProductRequest("iphone12","Just for test",BigDecimal.valueOf(1200.00));
        String productRequestString=objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product-service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString)
        ).andExpect(status().isCreated());
    }

    private ProductRequest getProductRequest(String name,String desc,BigDecimal price) {
        return ProductRequest.builder()
                .name(name)
                .description(desc)
                .price(price)
                .build();
    }

    @Test
    void getAllProducts() throws Exception {
        ObjectMapper objectMapper= new ObjectMapper();
        ProductRequest productRequest1 = getProductRequest("iphone12","Just for test",BigDecimal.valueOf(1200.00));
        ProductRequest productRequest2 = getProductRequest("iphone12","Just for test",BigDecimal.valueOf(1200.00));
        ProductRequest productRequest3 = getProductRequest("iphone12","Just for test",BigDecimal.valueOf(1200.00));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product-service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest1))).andExpect(status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product-service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest2))).andExpect(status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product-service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest3))).andExpect(status().isCreated());
        Assertions.assertEquals(3, productRepository.findAll().size());

    }
}
