package com.chinaka.task.order.producer.mintyn.service;

import com.chinaka.task.order.producer.mintyn.dto.GenericResponse;
import com.chinaka.task.order.producer.mintyn.dto.OrderDto;

public interface OrderService {
    GenericResponse addOrder(OrderDto order);
}
