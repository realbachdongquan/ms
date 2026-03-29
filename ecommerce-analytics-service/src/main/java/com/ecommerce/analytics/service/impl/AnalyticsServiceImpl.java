package com.ecommerce.analytics.service.impl;

import com.ecommerce.analytics.dto.AdvertisementDto;
import com.ecommerce.analytics.entity.Analytics;
import com.ecommerce.analytics.repository.AnalyticsRepository;
import com.ecommerce.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final AnalyticsRepository AnalyticsRepository;

    @Override
    public void createAnalytics(AdvertisementDto advertisementDto) {

        LOGGER.info("MessageServiceImpl | createAnalytics is started");

        Analytics Analytics = new Analytics();
        Analytics.setAdvertisementId(advertisementDto.getId());
        Analytics.setViewed(advertisementDto.getViewCount());

        Analytics.setMessage("Advertisement Id: " + Analytics.getAdvertisementId()
                + "Advertisement Title : " + advertisementDto.getTitle()
                + " Viewed: " + Analytics.getViewed()
                + " createdAt:" + LocalDateTime.now());

        LOGGER.info("MessageServiceImpl | createAnalytics | Analytics message : " + Analytics.getMessage());

        AnalyticsRepository.save(Analytics);
    }
}
