package it.course.piadac4.payload.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DockAddMultipleIngredientRequest {

	@NotEmpty private List<DockAddOneIngredientRequest> ingredients;
}
