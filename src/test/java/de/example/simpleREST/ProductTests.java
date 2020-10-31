package de.example.simpleREST;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.example.simpleREST.model.Product;
import de.example.simpleREST.model.ProductRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTests {

	@Autowired
	ProductRepository productRepository;

	Product[] testProducts = {
		new Product(null, "P1", new BigDecimal("10.50"), null, false),
		new Product(null, "P2", new BigDecimal("20.30"), null, false)
	};

	@Test
	@Order(0)
	void create() {
		productRepository.saveAll(Arrays.asList(testProducts));
	}

	@Test
	@Order(1)
	void listAndDelete() {
		List<Product> products = productRepository.findAll();
		assertEquals(2, products.size());
		
		productRepository.delete(products.get(0));
		
		List<Product> productsAfterDelete = productRepository.findAll();
		assertEquals(1, productsAfterDelete.size());

		List<Product> productsDeleted = productRepository.findDeleted();
		assertEquals(1, productsDeleted.size());
	}

}
