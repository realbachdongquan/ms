package com.ecommerce.category.service;

import com.ecommerce.category.dto.AdvertisementRequest;
import com.ecommerce.category.model.Advertisement;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    ResponseEntity<?> createAdvertisement(AdvertisementRequest advertisementRequest, String userId);

    ResponseEntity<?> updateAdvertisement(AdvertisementRequest advertisementRequest, String advertisementId);

    ResponseEntity<?> deleteAdvertisement(String advertisementId);

    List<Advertisement> getAllAdvertisements();

    ResponseEntity<Advertisement> getAdvertisementById(String advertisementId);

    ResponseEntity<?> approveAdvertisement(String advertisementId);

    ResponseEntity<?> rejectAdvertisement(String advertisementId);
}
