package it.course.piadac4.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.Order;
import it.course.piadac4.entity.ShoppingCartItemSupplement;
import it.course.piadac4.payload.response.ItemSupplementResponse;

@Repository
public interface ShoppingCartItemSupplementRepository extends JpaRepository<ShoppingCartItemSupplement, Long>{
	
	@Query(value="SELECT new it.course.piadac4.payload.response.ItemSupplementResponse("
		+ "scis.shoppingCartItemSupplementId.ingredient.ingredientName,"
		+ "scis.qty,"
		+ "p.price"
		+ ") "
		+ "FROM ShoppingCartItemSupplement scis "
		+ "INNER JOIN Price p ON p.priceId.ingredient.ingredientId=scis.shoppingCartItemSupplementId.ingredient.ingredientId "
		+ "WHERE scis.shoppingCartItemSupplementId.shoppingCartItem.shoppingCartItemtId=:shoppingCartItemtId "
		+ "AND :orderDate BETWEEN p.priceId.startDate AND p.endDate "
		)
	List<ItemSupplementResponse> getItemSupplementsByOrder(@Param("shoppingCartItemtId") long shoppingCartItemtId, @Param("orderDate") Date orderDate);
	
}
