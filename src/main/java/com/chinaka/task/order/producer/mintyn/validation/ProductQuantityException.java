package com.chinaka.task.order.producer.mintyn.validation;


import lombok.Data;

@Data
public class ProductQuantityException extends Exception {
    private final String message;
    public ProductQuantityException(String message) {
       this.message = message;
    }
}
