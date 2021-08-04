package com.trthhrts.buter.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderInfo {
    private Long id;
    private String status;
    private LocalDateTime orderDate;
    private LocalDateTime paymentDate;
    private Long cost;
}
