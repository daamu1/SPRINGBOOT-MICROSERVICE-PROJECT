package com.daamu;

import com.daamu.model.Inventory;
import com.daamu.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
		}
	@Bean
	public CommandLineRunner loadData (InventoryRepository inventoryRepository ){
		return args->{
			Inventory inventory=new Inventory();
			inventory.setSkuCode("iphone12-red");
			inventory.setQuantity(100);
			Inventory inventory2=new Inventory();
			inventory2.setSkuCode("iphone12-black");
			inventory2.setQuantity(200);
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory2);

		};
	}

	}
