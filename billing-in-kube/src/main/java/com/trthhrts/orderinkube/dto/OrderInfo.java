package com.trthhrts.orderinkube.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderInfo {

    private Long id;
    private String status;
    private LocalDateTime orderDate;
    private LocalDateTime paymentDate;
    private Long cost;
    private Long userId;
    private List<PositionInfo> positions = new ArrayList<>();
}
