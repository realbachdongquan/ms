package com.ecommerce.product.service.impl;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;


    @Override
    public void sendMessage(Product Product) {

        LOGGER.info("MessageServiceImpl | sendMessage is started");

        ProductDto ProductDto = new ProductDto();
        ProductDto.setId(Product.getId());
        ProductDto.setTitle(Product.getTitle());
        ProductDto.setViewCount(Product.getViewCount());

        LOGGER.info("MessageServiceImpl | sendMessage | | queue name : " + queue.getName());
        LOGGER.info("MessageServiceImpl | sendMessage | Sending message through RabbitMq");

        try {
            rabbitTemplate.convertAndSend(queue.getName(),ProductDto);
        }catch (Exception e){
            LOGGER.info("MessageServiceImpl | sendMessage | error : " + e.getMessage());
        }
    }
}
