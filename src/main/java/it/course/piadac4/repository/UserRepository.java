package it.course.piadac4.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.User;
import it.course.piadac4.payload.response.UserOrderResponse;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);	
	Optional<User> findByUsernameOrEmail(String username, String email);
	Optional<User> findByIdAndEnabledTrue(Long id);
	Optional<User> findByUsername(String username);
	Optional<User> findByUsernameAndEnabledTrue(String username);
	
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);	
	Boolean existsByUsernameOrEmail(String username, String email);
	
	List<User> findAllByEnabledTrue();
	
	@Query(value = "SELECT DISTINCT ua.user_id " +
			"FROM user_authorities ua JOIN authorities a ON ua.authority_id = a.id" +
			" WHERE a.name = :role", nativeQuery = true)
	List<Long> findUsersByRole(@Param("role") String role);
	
	@Query(value="SELECT NEW it.course.piadac4.payload.response.UserOrderResponse("
			+ "o.shoppingCart.user.lastname,"
			+ "o.shoppingCart.user.firstname,"
			+ "o.shoppingCart.user.email,"
			+ "o.shoppingCart.user.telephone,"
			+ "o.userAddress.userAddress,"
			+ "o.userAddress.userCity"
			+ ")"
			+ "FROM Order o "
			+ "WHERE o.orderId=:orderId")
	UserOrderResponse getUserByOrder(@Param("orderId") long orderId);
	
	

}
