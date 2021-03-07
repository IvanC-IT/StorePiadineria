package it.course.piadac4.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ItemSupplementResponse {
	
	private String ingredientName;
	private int qty;
	private double supplementPrice;

}
