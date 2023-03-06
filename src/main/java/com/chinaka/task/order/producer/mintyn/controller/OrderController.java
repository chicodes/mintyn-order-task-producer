package com.chinaka.task.order.producer.mintyn.controller;


import com.chinaka.task.order.producer.mintyn.dto.GenericResponse;
import com.chinaka.task.order.producer.mintyn.dto.OrderDto;
import com.chinaka.task.order.producer.mintyn.service.OrderService;
import com.chinaka.task.order.producer.mintyn.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping(Constants.ORDER_BASE_URL+"/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> addOrder(@RequestBody OrderDto request) {

        GenericResponse resp = orderService.addOrder(request);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }
}
