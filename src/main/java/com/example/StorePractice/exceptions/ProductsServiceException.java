package com.example.StorePractice.exceptions;

public class ProductsServiceException extends RuntimeException{
    public ProductsServiceException(String message){
        super(message);
    }

}
