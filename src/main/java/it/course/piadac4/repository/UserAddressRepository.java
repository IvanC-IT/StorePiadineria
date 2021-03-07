package it.course.piadac4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.UserAddress;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long>{
	
	
	

}
