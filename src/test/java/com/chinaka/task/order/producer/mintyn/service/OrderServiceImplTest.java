//package com.chinaka.task.order.producer.mintyn.service;
//
//import com.chinaka.task.order.producer.mintyn.dto.GenericResponse;
//import com.chinaka.task.order.producer.mintyn.dto.OrderDto;
//import com.chinaka.task.order.producer.mintyn.model.Product;
//import com.chinaka.task.order.producer.mintyn.repository.ProductRepository;
//import com.chinaka.task.order.producer.mintyn.util.ResponseHelper;
//import com.chinaka.task.order.producer.mintyn.validation.ProductExistException;
//import com.chinaka.task.order.producer.mintyn.validation.ProductQuantityException;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import java.util.Objects;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.kafka.core.KafkaTemplate;
//
//public class OrderServiceTest {
//
//    private OrderService orderService;
//    private ProductRepository productRepository;
//    private KafkaTemplate<String, String> kafkaTemplate;
//    private ResponseHelper responseHelper;
//    private String topicName = "order";
//
//    @Before
//    public void setUp() {
//        productRepository = mock(ProductRepository.class);
//        kafkaTemplate = mock(KafkaTemplate.class);
//        responseHelper = mock(ResponseHelper.class);
//        orderService = new OrderService(productRepository, kafkaTemplate, responseHelper);
//    }
//
//    @Test
//    public void testAddOrderSuccess() throws Exception {
//        OrderDto request = new OrderDto();
//        request.setProductId("1");
//        request.setQuantity("1");
//
//        Product product = new Product();
//        product.setId(1L);
//        product.setQuantity(2);
//
//        when(productRepository.findProductById(1L)).thenReturn(product);
//
//        GenericResponse expectedResponse = new GenericResponse();
//        expectedResponse.setRespCode("201");
//        expectedResponse.setRespDescription("Success");
//
//        when(responseHelper.getResponse("201", "Success", null, HttpStatus.CREATED))
//                .thenReturn(expectedResponse);
//
//        orderService.addOrder(request);
//
//        verify(kafkaTemplate).send(topicName, "{'productId': '1', 'quantity': '1'}");
//    }
//
//    @Test
//    public void testAddOrderProductNotFound() throws Exception {
//        OrderDto request = new OrderDto();
//        request.setProductId("1");
//        request.setQuantity("1");
//
//        when(productRepository.findProductById(1L)).thenReturn(null);
//
//        assertThrows(ProductExistException.class, () -> {
//            orderService.addOrder(request);
//        });
//    }
//
//    @Test
//    public void testAddOrderQuantityExceeded() throws Exception {
//        OrderDto request = new OrderDto();
//        request.setProductId("1");
//        request.setQuantity("2");
//
//        Product product = new Product();
//        product.setId(1L);
//        product.setQuantity(1);
//
//        when(productRepository.findProductById(1L)).thenReturn(product);
//
//        assertThrows(ProductQuantityException.class, () -> {
//            orderService.addOrder(request);
//        });
//    }
//}
