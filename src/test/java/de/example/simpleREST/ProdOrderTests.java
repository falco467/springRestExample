package de.example.simpleREST;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import de.example.simpleREST.model.ProdOrder;
import de.example.simpleREST.model.ProdOrderRepository;
import de.example.simpleREST.model.Product;
import de.example.simpleREST.model.ProductRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdOrderTests {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProdOrderRepository prodOrderRepository;

	Product[] testProducts = {
		new Product(null, "O1", new BigDecimal("10.50"), null, false),
		new Product(null, "O2", new BigDecimal("20.30"), null, false)
	};

	<T> List<T> toList(Iterable<T> iterable) {
		ArrayList<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}

	@Test
	@Order(0)
	void create() {
		List<Product> products = toList(productRepository.saveAll(Arrays.asList(testProducts)));

		prodOrderRepository.save(new ProdOrder(null, "test@mail.com", null, products));
		prodOrderRepository.save(new ProdOrder(null, "test2@mail.com", null, Arrays.asList(products.get(0))));
	}

	@Test
	@Order(1)
	@Transactional // Needed to access products collection without LazyLoad Exception
	void totalAmount() {
		List<ProdOrder> orders = toList(prodOrderRepository.findAll());
		assertEquals(2, orders.size());

		assertEquals(
			testProducts[0].getPrice().add(testProducts[1].getPrice()),
			orders.get(0).getTotalAmount());
	}

	@Test
	@Order(2)
	void rangeQuery() {
		List<ProdOrder> ordersAll = toList(prodOrderRepository.findAll());
		assertEquals(2, ordersAll.size(), "All Orders do not match");

		List<ProdOrder> orders = toList(prodOrderRepository.findInRange(
			LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1)));
			assertEquals(2, orders.size(), "Range orders do not match");
		
		List<ProdOrder> noOrders = toList(prodOrderRepository.findInRange(
			LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1)));
		assertEquals(0, noOrders.size());
	}

}
