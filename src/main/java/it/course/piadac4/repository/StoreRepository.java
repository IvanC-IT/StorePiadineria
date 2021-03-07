package it.course.piadac4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.ShoppingCart;
import it.course.piadac4.entity.Store;
import it.course.piadac4.payload.response.StoreResponse;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{
	
	Optional<Store> findByStoreIdAndOpenTrue(long storeId);
	
	
	@Query(value="SELECT new it.course.piadac4.payload.response.StoreResponse("
			+ "o.store.storeName,"
			+ "o.store.storeAddress,"
			+ "o.store.storeCity,"
			+ "o.store.storeEmail,"
			+ "o.store.storePhone"
			+ ") "
			+ "FROM Order o "
			+ "WHERE o.orderId=:orderId")
	StoreResponse getStoreByOrder(@Param("orderId") long orderId);

}
