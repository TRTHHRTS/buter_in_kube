package com.trthhrts.buter.controllers;

import com.trthhrts.buter.dto.PositionsInfo;
import com.trthhrts.buter.service.remote.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {

    @Value("${services.auth-service.url}")
    private String authUri;

    private final OrderService orderService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("buters", orderService.getButers());
        model.addAttribute("authServiceUrl", authUri);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("authServiceUrl", authUri);
        return "login";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "orders";
    }

    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity<String> creteOrder(@RequestBody Map<Integer, Integer> createOrderInfo) {
        if (createOrderInfo.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<PositionsInfo> positions = new ArrayList<>();
        for (Integer buterId : createOrderInfo.keySet()) {
            positions.add(new PositionsInfo(buterId, createOrderInfo.get(buterId)));
        }
        orderService.createOrder(positions);
        return ResponseEntity.ok("OK");
    }
}