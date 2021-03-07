package it.course.piadac4.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import it.course.piadac4.entity.Ingredient;
import it.course.piadac4.payload.response.IngredientItemShoppingCartResponse;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
	
	Optional<Ingredient> findByIngredientName(String ingredientName);
	
	
//	@Query(value= "SELECT ic.INGREDIENT_ID, ic.PIECE FROM SHOPPING_CART_ITEM AS "
//			+ "sci INNER JOIN ITEM_CONTAINS as ic ON :itemId = :itemId "
//			+"WHERE sci.SHOPPING_CART_ID = :scId ", nativeQuery=true)
//	 List<IngredientItemResponse> findItemByPiece(@Param("itemId") long itemId, @Param("scId") long scId);
	
	
//	@Query(value= "SELECT ic.INGREDIENT_ID, ic.PIECE FROM SHOPPING_CART_ITEM AS "
//			+ "sci INNER JOIN ITEM_CONTAINS as ic ON :itemId = ic.ITEM_ID "
//			+"WHERE sci.SHOPPING_CART_ID = :scId ", nativeQuery=true)
//	List<Object[]> findItemByPiece(@Param("itemId") long itemId, @Param("scId") long scId);

	@Query(value= " SELECT new it.course.piadac4.payload.response.IngredientItemShoppingCartResponse ( "
			+ "ic.itemContainsId.ingredient.ingredientId, "
			+ "ic.piece"
			+ ") "
			+ "FROM ShoppingCartItem sci "
			+ "INNER JOIN ItemContains ic ON sci.item.itemId = ic.itemContainsId.item.itemId "
			+ "WHERE sci.shoppingCartItemtId = :scId ")
	List<IngredientItemShoppingCartResponse> findItemByPiece( @Param("scId") long scId);
			



//	@Query(@Query(value= "SELECT ic.INGREDIENT_ID, ic.PIECE FROM SHOPPING_CART_ITEM AS "
////			+ "sci INNER JOIN ITEM_CONTAINS as ic ON :itemId = :itemId "
////			+"WHERE sci.SHOPPING_CART_ID = :scId " 
//			 
//			)
//	List<Ingredient> getUserByRole(@Param("role") String role);
}
