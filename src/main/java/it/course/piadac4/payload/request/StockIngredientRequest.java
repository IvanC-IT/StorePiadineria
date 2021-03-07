package it.course.piadac4.payload.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class StockIngredientRequest {

	@NotNull
	private long ingredientId;
	
	@NotNull @Min(0) @Max(2147483646)
	private int qty;
	
	//@NotNull
	//private long unitId;
}