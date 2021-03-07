package it.course.piadac4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.OrderStatus;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long>{
	
	Optional<OrderStatus> findByOrderStatusName(String orderStatusName);
	

}
