package com.learnp.kafka.demo.service;

import com.learnp.kafka.demo.CreateProductRequest;

import java.util.concurrent.ExecutionException;

public interface ProductService {

    String createProduct(CreateProductRequest request) throws ExecutionException, InterruptedException;
}
