package com.chinaka.task.order.producer.mintyn.util;

import com.chinaka.task.order.producer.mintyn.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseHelper <T> {

    public GenericResponse getResponse (String failedCode, String message, T body, HttpStatus httpStatus) {
        GenericResponse response = new GenericResponse();
        response.setRespCode(failedCode);
        response.setRespDescription(message);
        response.setRespBody(body);
        response.setHttpStatus(httpStatus);
        return response;
    }
}
