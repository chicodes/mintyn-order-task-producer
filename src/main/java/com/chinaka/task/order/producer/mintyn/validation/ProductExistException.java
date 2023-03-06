package com.chinaka.task.order.producer.mintyn.validation;


import lombok.Data;

@Data
public class ProductExistException extends Exception {
    private final String message;
    public ProductExistException(String message) {
       this.message = message;
    }
}
