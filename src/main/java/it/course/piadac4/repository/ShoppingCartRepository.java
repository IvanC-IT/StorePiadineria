package it.course.piadac4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.ShoppingCart;
import it.course.piadac4.entity.User;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{
	
	boolean existsByUserAndCheckoutFalse(User u);
	
	Optional<ShoppingCart> findByUserAndCheckoutFalse(User u);
	
	

}
