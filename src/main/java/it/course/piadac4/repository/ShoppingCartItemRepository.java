package it.course.piadac4.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.ShoppingCart;
import it.course.piadac4.entity.ShoppingCartItem;
import it.course.piadac4.entity.User;

import it.course.piadac4.payload.response.ItemOrderResponse;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long>{
	
	/*
	@Query(value="DELETE FROM ShoppingCartItem s WHERE s.shoppingCartItemId=:shoppingCartItemId")
	void deleteShoppingCartItem(long shoppingCartItemId);
	*/
	
	
	@Query(value="SELECT COUNT(ITEM_ID) FROM SHOPPING_CART_ITEM WHERE SHOPPING_CART_ID=:shoppingCartId", nativeQuery=true)
	long countShoppingCartItems(@Param("shoppingCartId") long shoppingCartId);

	/*
	@Query(value="SELECT new it.course.piadac4.payload.response.ItemOrderResponse("
			+ "sci.shoppingCartItemtId,"
			+ "sci.item.itemName,"
			+ "(CASE WHEN "
			+ "EXISTS (SELECT scis.shoppingCartItemSupplementId.shoppingCartItem "
			+ "FROM ShoppingCartItemSupplement scis "
			+ "WHERE sci=scis.shoppingCartItemSupplementId.shoppingCartItem)  "
			+ "THEN "
			+ ""
			+ "((SELECT SUM(COALESCE(p1.price,0)*COALESCE(ic1.piece,1)) FROM Price p1 "
			+ "RIGHT JOIN ItemContains ic1 ON (p1.priceId.ingredient.ingredientId=ic1.itemContainsId.ingredient.ingredientId "
			+ "AND sci.item=ic1.itemContainsId.item))"
			+ "+"
			+ "(SELECT SUM(COALESCE(p2.price,0)*scis2.qty) FROM Price p2 "
			+ "RIGHT JOIN ShoppingCartItemSupplement scis2 ON sci=scis2.shoppingCartItemSupplementId.shoppingCartItem "
			+ "WHERE p2.priceId.ingredient=scis2.shoppingCartItemSupplementId.ingredient)) "
			+ ""
			+ " ELSE "
			+ "(SELECT SUM(COALESCE(p3.price,0)*COALESCE(ic.piece,1)) FROM Price p3 "
			+ "INNER JOIN ItemContains ic ON (p3.priceId.ingredient.ingredientId=ic.itemContainsId.ingredient.ingredientId "
			+ "AND sci.item=ic.itemContainsId.item)) "
			+ "END)"
			+ ") "
			+ "FROM ShoppingCartItem sci "
			+ "INNER JOIN Order o ON (sci.shoppingCart.shoppingCartId=o.shoppingCart.shoppingCartId) "
			+ "WHERE o.orderId=:orderId "
			)*/

	@Query(value="SELECT new it.course.piadac4.payload.response.ItemOrderResponse("
			+ "sci.shoppingCartItemtId,"
			+ "sci.item.itemName,"
			+ "(CASE WHEN "
			+ "EXISTS (SELECT scis.shoppingCartItemSupplementId.shoppingCartItem "
			+ "FROM ShoppingCartItemSupplement scis "
			+ "WHERE sci=scis.shoppingCartItemSupplementId.shoppingCartItem) AND "
			+ "EXISTS (SELECT p4.price FROM Price p4 "
			+ "RIGHT JOIN ShoppingCartItemSupplement scis3 ON sci=scis3.shoppingCartItemSupplementId.shoppingCartItem "
			+ " WHERE o.orderDate BETWEEN p4.priceId.startDate "
			+ "AND p4.endDate AND p4.priceId.ingredient=scis3.shoppingCartItemSupplementId.ingredient) "
			+ ""
			+ "THEN "
			+ "((SELECT SUM(COALESCE(p1.price,0)*COALESCE(ic1.piece,1)) FROM Price p1 "
			+ "RIGHT JOIN ItemContains ic1 ON (p1.priceId.ingredient.ingredientId=ic1.itemContainsId.ingredient.ingredientId "
			+ "AND sci.item=ic1.itemContainsId.item) "
			+ "WHERE o.orderDate BETWEEN p1.priceId.startDate "
			+ "AND p1.endDate)"
			+ "+"
			+ "(SELECT SUM(COALESCE(p2.price,0)*scis2.qty) FROM Price p2 "
			+ "RIGHT JOIN ShoppingCartItemSupplement scis2 ON sci=scis2.shoppingCartItemSupplementId.shoppingCartItem "
			+ "WHERE p2.priceId.ingredient=scis2.shoppingCartItemSupplementId.ingredient "
			+ "AND o.orderDate BETWEEN p2.priceId.startDate "
			+ "AND p2.endDate))"
			+ ""
			+ " ELSE "
			+ "(SELECT SUM(COALESCE(p3.price,0)*COALESCE(ic.piece,1)) FROM Price p3 "
			+ "INNER JOIN ItemContains ic ON (p3.priceId.ingredient.ingredientId=ic.itemContainsId.ingredient.ingredientId "
			+ "AND sci.item=ic.itemContainsId.item) "
			+ "WHERE o.orderDate BETWEEN p3.priceId.startDate "
			+ "AND p3.endDate) "
			+ "END)"
			+ ") "
			+ "FROM ShoppingCartItem sci "
			+ "INNER JOIN Order o ON (sci.shoppingCart=o.shoppingCart) "
			+ "WHERE o.orderId=:orderId "
			)
	List<ItemOrderResponse> getItemsByOrder(@Param("orderId") long orderId);

	
	
	
	
	
}
