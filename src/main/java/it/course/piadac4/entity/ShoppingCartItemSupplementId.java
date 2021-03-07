package it.course.piadac4.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class ShoppingCartItemSupplementId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SHOPPING_CART_ITEM_ID", nullable=false)
	private ShoppingCartItem shoppingCartItem;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INGREDIENT_ID", nullable=false)
	private Ingredient ingredient;

}
