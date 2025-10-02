package com.learnp.kafka.demo.service;

import com.learnp.kafka.demo.CreateProductRequest;
import com.learnp.kafka.demo.ProductCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{

    private final KafkaTemplate<String , ProductCreateEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String , ProductCreateEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public String createProduct(CreateProductRequest request) throws ExecutionException, InterruptedException {
        String productId = String.valueOf(UUID.randomUUID());
        ProductCreateEvent productCreateEvent =
                new ProductCreateEvent(productId , request.getTitle(),
                        request.getPrice(), request.getQuantity());
        //asynchronously send the message to kafka topic
//        CompletableFuture<SendResult<String, ProductCreateEvent>> result
//                = kafkaTemplate.send("product-event" , productId , productCreateEvent);
//
//        result.whenComplete((res , ex) -> {
//            if(ex == null){
//                log.info("Successfully sent message : " + res.getProducerRecord().value()
//                + " with offset : " + res.getRecordMetadata().offset());
//            } else {
//                log.info("Unable to send message : " + ex.getMessage());
//            }
//        });

        //synchronously send the message to kafka topic
        SendResult<String , ProductCreateEvent> sendResult
                = kafkaTemplate.send("product-event" , productId , productCreateEvent)
                .get();
        log.info("topic : " + sendResult.getRecordMetadata().topic());
        log.info("partition : " + sendResult.getRecordMetadata().partition());
        log.info("offset : " + sendResult.getRecordMetadata().offset());
        log.info("timestamp : " + sendResult.getRecordMetadata().timestamp());
        log.info("Product create event sent to kafka for product id : " + productId);
        return productId;
    }
}
