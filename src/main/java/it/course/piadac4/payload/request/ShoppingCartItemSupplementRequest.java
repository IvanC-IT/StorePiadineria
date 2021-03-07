package it.course.piadac4.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class ShoppingCartItemSupplementRequest {
	
	private long shoppingCartItemId;
	private long ingredientId;
	private int qty;

}
