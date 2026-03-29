package com.ecommerce.analytics.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Analyticss")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Analytics extends IdBasedEntity implements Serializable {

    private Long advertisementId;
    private Long viewed;
    private String message;
}
