package com.ecommerce.analytics.repository;

import com.ecommerce.analytics.entity.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository extends JpaRepository<Analytics,Long> {

}
