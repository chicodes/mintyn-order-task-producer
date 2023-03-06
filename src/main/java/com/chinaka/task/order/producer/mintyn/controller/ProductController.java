package com.chinaka.task.order.producer.mintyn.controller;


import com.chinaka.task.order.producer.mintyn.dto.ProductDto;
import com.chinaka.task.order.producer.mintyn.dto.GenericResponse;
import com.chinaka.task.order.producer.mintyn.service.ProductService;
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
@RequestMapping(Constants.ORDER_BASE_URL+"/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> addProduct(@RequestBody @Validated(ProductDto.Validation.class) ProductDto request) {

        GenericResponse resp = productService.addProduct(request);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }


    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getProduct(@PathVariable String id) {

        GenericResponse resp = productService.getProduct(id);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> updateProduct(@PathVariable String id, @RequestBody ProductDto request) {

        GenericResponse resp = productService.updateProduct(id, request);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }

    @GetMapping("")
    public ResponseEntity<GenericResponse> listProduct(@RequestParam (defaultValue = "0")int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        GenericResponse resp = productService.listProduct(pageNo, pageSize);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deletProduct(@PathVariable String id) {

        GenericResponse resp = productService.deleteProduct(id);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }
}
