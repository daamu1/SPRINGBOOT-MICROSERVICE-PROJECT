package com.daamu.controller;

import com.daamu.DTO.InventoeryResponse;
import com.daamu.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    //http://localhost:8083/api/inventory/iphone13
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoeryResponse> isInStock(@RequestParam("skuCode") List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
