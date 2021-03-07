package it.course.piadac4.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="SHOPPING_CART_ITEM_SUPPLEMENT")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ShoppingCartItemSupplement {
	
	@EmbeddedId
	private ShoppingCartItemSupplementId shoppingCartItemSupplementId;
	
	@Column(name="QTY", nullable=false, columnDefinition="TINYINT(2)")
	private int qty;

}
