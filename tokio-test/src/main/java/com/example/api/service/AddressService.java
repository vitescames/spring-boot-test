package com.example.api.service;

import java.net.URLEncoder;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.api.domain.Address;
import com.example.api.repository.AddressRepository;

@Service
public class AddressService {
	
	private AddressRepository repository;
	
	private String uri = "https://viacep.com.br/ws/";

	@Autowired
	public AddressService(AddressRepository repository) {
		this.repository = repository;
	}
	
	public List<Address> findAll() {		
		return repository.findAllByOrderByCityAsc();
	}
	
	public Address insertAddress(String zipCode) throws JSONException {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String response = restTemplate.getForObject(uri + zipCode + "/json", String.class);
		JSONObject object = new JSONObject(response);
		
		Address address = new Address();
		address.setZipCode(object.getString("cep"));
		address.setCity(object.getString("localidade"));
		address.setDistrict(object.getString("bairro"));
		address.setStreet(object.getString("logradouro"));
		
		return repository.save(address);
	}
}
