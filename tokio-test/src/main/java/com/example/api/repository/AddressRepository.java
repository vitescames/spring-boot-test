package com.example.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.api.domain.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{

	List<Address> findAllByOrderByCityAsc();
	
}
