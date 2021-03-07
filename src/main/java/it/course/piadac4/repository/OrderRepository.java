package it.course.piadac4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	

}
