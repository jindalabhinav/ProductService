package com.example.productservice.controllers.advices;

import com.example.productservice.controllers.ProductController;
import com.example.productservice.dtos.ExceptionDto;
import com.example.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = {ProductController.class})
public class ProductControllerAdvice {
    @ExceptionHandler(ProductNotFoundException.class)
    // Below 2 lines are similar to creating a ResponseEntity
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    private ExceptionDto handleProductNotFoundException(ProductNotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setStatus("Failure");
//        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
        return exceptionDto;
    }
}
