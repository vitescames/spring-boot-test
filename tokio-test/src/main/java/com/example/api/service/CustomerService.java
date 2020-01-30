package com.example.api.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.exception.CustomerNameException;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}
	
	public Page<Customer> findAllPageable(Optional<Integer> page) {
		return repository.findByName("_", PageRequest.of(page.orElse(0), 3));
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}
	
	public Customer insertCustomer(Customer customer) throws Exception {
		
		if(customer.getName().length() <= 3) {
			throw new CustomerNameException("Invalid Customer Name");
		}
		
		return repository.save(customer);
	}
	
	public Customer updateCustomer(Customer newCustomer, Long id) throws Exception {
		
		if(newCustomer.getName().length() <= 3) {
			throw new CustomerNameException("Invalid Customer Name");
		}
		
		Optional<Customer> oldCustomer = this.findById(id);
		
		if(oldCustomer.isPresent()) {
			Customer customer = oldCustomer.get();
			customer.setEmail(newCustomer.getEmail());
			customer.setName(newCustomer.getName());
			return repository.save(customer);
		} else {
			return null;
		}
		
	}
	
	public boolean deleteCustomer(Long id) {
		
		Optional<Customer> customer = this.findById(id);
		
		if(customer.isPresent()) {
			repository.delete(customer.get());
			return true;
		} else {
			return false;
		}
		
	}

}
