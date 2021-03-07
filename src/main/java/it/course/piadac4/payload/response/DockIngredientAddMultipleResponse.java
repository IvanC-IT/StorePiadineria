package it.course.piadac4.payload.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class DockIngredientAddMultipleResponse {
	
	private List<DockIngredientErrorResponse> added;
	private List<DockIngredientErrorResponse> notAdded;
	
	public DockIngredientAddMultipleResponse() {
		super();
		added = new ArrayList<DockIngredientErrorResponse>();
		notAdded = new ArrayList<DockIngredientErrorResponse>();
	}
}
