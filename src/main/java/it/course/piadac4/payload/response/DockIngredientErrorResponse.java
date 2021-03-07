package it.course.piadac4.payload.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DockIngredientErrorResponse {
	
	private String ingredientName;
	private String message;
}
