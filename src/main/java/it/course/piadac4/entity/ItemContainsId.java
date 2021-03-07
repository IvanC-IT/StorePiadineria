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
public class ItemContainsId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="ITEM_ID", nullable=false)
	private Item item;
	
	@ManyToOne
	@JoinColumn(name="INGREDIENT_ID", nullable=false)
	private Ingredient ingredient;

}
