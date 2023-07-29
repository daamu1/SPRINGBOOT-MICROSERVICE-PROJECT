package com.daamu.service;

import com.daamu.DTO.InventoeryResponse;
import com.daamu.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    @Autowired
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoeryResponse> isInStock(List<String> skuCode)  {
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory -> InventoeryResponse.builder()
                        .skucode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity()>0)
                        .build())
                .toList();
    }
    }


