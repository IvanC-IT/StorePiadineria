package it.course.piadac4.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.DockIngredient;
import it.course.piadac4.entity.DockIngredientId;

@Repository
public interface DockIngredientRepository extends JpaRepository<DockIngredient, DockIngredientId>{
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO dock_ingredient "
			+ "(INGREDIENT_ID,QTY,UNIT_ID,STORE_ID) "
			+ "VALUES (:ingredientId,:qty,"
				+ "(SELECT UNIT_ID FROM PRICE WHERE INGREDIENT_ID=:ingredientId "
				+ "AND CURRENT_TIMESTAMP BETWEEN START_DATE AND END_DATE),"
			+ ":storeId) "
			+ "ON DUPLICATE KEY UPDATE QTY=QTY+:qty",nativeQuery=true)
	void insertDockIngredient(@Param("ingredientId") long ingredientId,
			@Param("qty") int qty,
			@Param("storeId") long storeId);

}
