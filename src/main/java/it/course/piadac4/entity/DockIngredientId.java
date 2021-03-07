package it.course.piadac4.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class DockIngredientId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="STORE_ID", nullable=false)
	private Store store;
	
	@ManyToOne
	@JoinColumn(name="INGREDIENT_ID", nullable=false)
	private Ingredient ingredient;
}
