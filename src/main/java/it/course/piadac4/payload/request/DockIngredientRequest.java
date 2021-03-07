package it.course.piadac4.payload.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DockIngredientRequest {

	@NotNull @Positive(message= "Qty cannot be null or negative")
	private int qtyId;

	@NotNull @Positive(message= "IngredientId cannot be null or negative")
	private Long ingredientId;

	@NotNull @Positive(message= "StoreId cannot be null or negative")
	private Long storeId;



}