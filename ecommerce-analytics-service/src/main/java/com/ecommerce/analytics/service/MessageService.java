package com.ecommerce.analytics.service;

import com.ecommerce.analytics.dto.AdvertisementDto;

public interface MessageService {
    void receiveMessage(AdvertisementDto advertisementDto);
}
