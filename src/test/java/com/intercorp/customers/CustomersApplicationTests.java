package com.intercorp.customers;

import com.intercorp.customers.controller.CustomerController;
import com.intercorp.customers.dto.CustomerDTO;
import com.intercorp.customers.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomersApplicationTests {
	@Autowired
	private CustomerService service;

	@Test
	void contextLoads() {
	}

	@Test
	public void testHelloWoldCustomer() {
		CustomerController customerController = new CustomerController();
		String result = customerController.hello();
		assertEquals(result, "Hello World");
	}

	@Test
	public void testAddCustomer() {
		CustomerDTO customer= new CustomerDTO();
		customer.setFirstname("Roberto");
		customer.setLastname("Gomez");
		customer.setAge(23);
		customer.setBirthday(new Date());
		Boolean result =  service.add(customer);
		assertTrue(result.equals(true));
	}

	@Test
	public void testUpdateCustomer() {
		CustomerDTO customer= new CustomerDTO();
		String id= "3njjYWPvKQimNcRD2GTe";
		customer.setFirstname("Mario");
		customer.setLastname("Martinez");
		customer.setAge(38);
		customer.setBirthday(new Date());
		Boolean result =  service.edit(id,customer);
		assertTrue(result.equals(true));
	}

	@Test
	public void testDeleteCustomer() {
		String id= "3njjYWPvKQimNcRD2GTe1";
		Boolean result =  service.delete(id);
		assertFalse(result.equals(false));
	}

	@Test
	public void testListCustomer() {
		List<CustomerDTO> result =  service.list();
		assertTrue(result.size() >0);
	}

	@Test
	public void testListEntityCustomer() {
		CustomerDTO customer;
		customer = service.listEntity("3njjYWPvKQimNcRD2GTe");
		assertTrue(customer!=null);
	}










}
