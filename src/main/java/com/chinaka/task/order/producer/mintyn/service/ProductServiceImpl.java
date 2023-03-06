package com.chinaka.task.order.producer.mintyn.service;

import com.chinaka.task.order.producer.mintyn.dto.*;
import com.chinaka.task.order.producer.mintyn.dto.ProductDto;
import com.chinaka.task.order.producer.mintyn.model.Product;
import com.chinaka.task.order.producer.mintyn.repository.ProductRepository;
import com.chinaka.task.order.producer.mintyn.util.*;
import com.chinaka.task.order.producer.mintyn.util.Constants;
import com.chinaka.task.order.producer.mintyn.validation.ProductExistException;
import com.chinaka.task.order.producer.mintyn.dto.GenericResponse;
import com.chinaka.task.order.producer.mintyn.util.ResponseHelper;
import com.chinaka.task.order.producer.mintyn.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

import static com.chinaka.task.order.producer.mintyn.util.Constants.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ResponseHelper responseHelper;

    @Override
    public GenericResponse addProduct(ProductDto request){

        try {
            Product product = new Product();
            log.info("creating new product");
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(new BigDecimal(request.getPrice()));
            product.setCurrencyCode(request.getCurrencyCode());
            product.setQuantity(Integer.parseInt(request.getQuantity()));
            product.setDateCreated(Utility.getCurrentDate());
            product.setStatus(request.getStatus());
            log.info("inserting product, {}", product);
            Product respBody = productRepository.save(product);

            return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, respBody, HttpStatus.CREATED);
        }
        catch (Exception e){
            return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, e.getMessage(), HttpStatus.OK);
        }
    }

    @Cacheable(cacheNames = {"productCache"}, key = "#id")
    @Override
    public GenericResponse getProduct(String id){
        try {
            log.info("getting product with Id {}", id);
            Product result = productRepository.findProductById(Long.valueOf(id));
            if (Objects.isNull(result)) {
                throw new ProductExistException(PRODUCT_NOT_FOUND);
            }
            log.info("response body:  {}", result);
            return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, result, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return responseHelper.getResponse(FAILED_CODE, e.getMessage(), "", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public GenericResponse listProduct(int pageNo, int pageSize){
        try {
            Pageable paging = PageRequest.of(pageNo, pageSize);
            log.info("getting all order");
            Page<Product> result = productRepository.findAll(paging);
            log.info("{}", result);
            if (result.isEmpty()) {
                return responseHelper.getResponse(Constants.NOT_FOUND_CODE, Constants.NOT_FOUND, null, HttpStatus.EXPECTATION_FAILED);
            }
            log.info("response body:  {}", result);
            return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, result, HttpStatus.OK);
        }
        catch(Exception e){
            return responseHelper.getResponse(FAILED_CODE, e.getMessage(), "", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @CachePut(cacheNames = {"productCache"}, key="#id")
    @Override
    public GenericResponse updateProduct(String id, ProductDto request){

        try {
            Product getProduct = productRepository.findProductById(Long.valueOf(id));
            if (Objects.nonNull(getProduct)) {
                log.info("updating order with ID {}", id);
                getProduct.setName(request.getName());
                getProduct.setDescription(request.getDescription());
                getProduct.setPrice(new BigDecimal(request.getPrice()));
                getProduct.setCurrencyCode(request.getCurrencyCode());
                getProduct.setQuantity(Integer.parseInt(request.getQuantity()));
                getProduct.setDateCreated(Utility.getCurrentDate());
                getProduct.setStatus(request.getStatus());
                Product result = productRepository.save(getProduct);
                log.info("response body:  {}", getProduct);
                return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, getProduct, HttpStatus.OK);
            }
            return responseHelper.getResponse(EXPECTATION_FAILED, PRODUCT_NOT_FOUND, null, HttpStatus.EXPECTATION_FAILED);
        }
        catch(Exception e){
            return responseHelper.getResponse(FAILED_CODE, e.getMessage(), "", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @CacheEvict(cacheNames = {"productCache"}, key = "#id")
    @Override
    public GenericResponse deleteProduct(String id) {
        try {
            Product getProduct = productRepository.findById(Long.valueOf(id))
                    .orElse(new Product());
            log.info("deleting order");
            productRepository.delete(getProduct);
            return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, null, HttpStatus.OK);
        }catch(Exception e){
            return responseHelper.getResponse(FAILED_CODE, e.getMessage(), "", HttpStatus.EXPECTATION_FAILED);
        }
    }
}
