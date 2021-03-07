package it.course.piadac4.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DockAddOneIngredientRequest {

	private String ingredientName;
	private int qty;
	private String unitMeasure;
	
}
