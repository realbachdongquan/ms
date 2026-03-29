package com.ecommerce.category.service;

import com.ecommerce.category.model.Advertisement;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<Advertisement> getAllAdvertisements();

    ResponseEntity<Advertisement> getAdvertisementById(String advertisementId);
}
