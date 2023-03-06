package com.chinaka.task.order.producer.mintyn.dto;

import com.chinaka.task.order.producer.mintyn.util.ProductStatus;
import lombok.Data;

@Data
public class ResponseData {
        private long id;
        private String date;
        private String amount;
        private String currencyCode;
        private ProductStatus transactionType;
}
