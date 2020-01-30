package com.example.api.web.rest;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.api.domain.Address;
import com.example.api.service.AddressService;

@RestController
@RequestMapping("/adresses")
public class AddressController {
	
	private AddressService service;
	
	@Autowired
	public AddressController(AddressService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<Address> findAll() throws Exception {
		return service.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Address> insertAddress(@Valid @RequestBody String zipCode, UriComponentsBuilder uriBuilder) throws Exception{
		Address addressCreated = service.insertAddress(zipCode);
		UriComponents uriComponents = uriBuilder.path("/adresses/{id}").buildAndExpand(addressCreated.getId());
		URI uri = uriComponents.toUri();
		
		return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(addressCreated);
	}

}
