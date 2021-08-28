package com.trthhrts.buter.controllers;

import com.trthhrts.buter.dto.Buter;
import com.trthhrts.buter.dto.OrderInfo;
import com.trthhrts.buter.dto.PositionsInfo;
import com.trthhrts.buter.service.remote.BillingService;
import com.trthhrts.buter.service.remote.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    @Value("${services.auth-service.external_url}")
    private String authUri;

    private final OrderService orderService;

    private final BillingService billingService;

    @GetMapping("/")
    public String index(Model model) {
        Buter[] buters = orderService.getButers();
        model.addAttribute("buters", Arrays.stream(buters).sorted(Comparator.comparingLong(Buter::getId)).toArray());
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
        model.addAttribute("authServiceUrl", authUri);
        try {
            Object[] orders = Arrays.stream(orderService.getOrders()).sorted(Comparator.comparingLong(OrderInfo::getId)).toArray();
            model.addAttribute("orders", orders);
        } catch (Exception e) {
            model.addAttribute("ERROR", "ОШИБКА ЗАГРУЗКИ СПИСКА ЗАКАЗОВ: " + e.getMessage());
        }
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
        try {
            Long orderId = orderService.createOrder(positions);
            billingService.payOrder(orderId);
        } catch (Exception e) {
            log.error("Ошибка создания заказа", e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/order/{id}/done")
    @ResponseBody
    public ResponseEntity<String> doneOrder(@PathVariable Long id) {
        orderService.doneOrder(id);
        return ResponseEntity.ok("OK");
    }
}