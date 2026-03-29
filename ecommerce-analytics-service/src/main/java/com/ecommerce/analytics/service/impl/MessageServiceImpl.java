package com.ecommerce.analytics.service.impl;

import com.ecommerce.analytics.dto.AdvertisementDto;
import com.ecommerce.analytics.service.MessageService;
import com.ecommerce.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final AnalyticsService AnalyticsService;

    @RabbitListener(queues = "${queue.name}")
    @Override
    public void receiveMessage(@Payload AdvertisementDto advertisementDto) {

        LOGGER.info("MessageServiceImpl | receiveMessage is started");

        LOGGER.info("MessageServiceImpl | receiveMessage | Analytics is creating");

        AnalyticsService.createAnalytics(advertisementDto);
    }
}
