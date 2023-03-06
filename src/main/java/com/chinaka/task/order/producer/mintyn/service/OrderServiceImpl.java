package com.chinaka.task.order.producer.mintyn.service;

import com.chinaka.task.order.producer.mintyn.dto.GenericResponse;
import com.chinaka.task.order.producer.mintyn.model.Order;
import com.chinaka.task.order.producer.mintyn.dto.OrderDto;
import com.chinaka.task.order.producer.mintyn.model.Product;
import com.chinaka.task.order.producer.mintyn.repository.OrderRepository;
import com.chinaka.task.order.producer.mintyn.repository.ProductRepository;
import com.chinaka.task.order.producer.mintyn.util.ResponseHelper;
import com.chinaka.task.order.producer.mintyn.util.Constants;
import com.chinaka.task.order.producer.mintyn.validation.ProductExistException;
import com.chinaka.task.order.producer.mintyn.validation.ProductQuantityException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.util.Objects;

import static com.chinaka.task.order.producer.mintyn.util.Constants.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final ProductRepository productRepository;

    private final ResponseHelper responseHelper;

    @Value("${topic.name.producer}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    @Override
    public GenericResponse addOrder(OrderDto request){

        try {
            Product getOrder = productRepository.findProductById(Long.valueOf(request.getProductId()));

            if(Objects.isNull(getOrder))
                throw new ProductExistException("Product Not found");
            Integer orderQuantity = Integer.valueOf(request.getQuantity());

            //check order quantity
            if( orderQuantity > getOrder.getQuantity())
                throw new ProductQuantityException("order quantity is beyond remaining quantity");

            log.info("sending order to kafka");
            ObjectMapper objectMapper = new ObjectMapper();
            String tojson = objectMapper.writeValueAsString(request);

            OrderDto orderjson = gson.fromJson(tojson, OrderDto.class);

            return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, orderjson, HttpStatus.CREATED);
        }
        catch (Exception e){
            return responseHelper.getResponse(FAILED_CODE, e.getMessage(), null, HttpStatus.EXPECTATION_FAILED);
        }
    }
}
