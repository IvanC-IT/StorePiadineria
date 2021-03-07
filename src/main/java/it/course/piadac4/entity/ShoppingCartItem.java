package it.course.piadac4.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="SHOPPING_CART_ITEM")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ShoppingCartItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SHOPPING_CART_ITEM_ID")
	private Long shoppingCartItemtId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SHOPPING_CART_ID", nullable=false, foreignKey = @ForeignKey(name = "FK_uno"))
	private ShoppingCart shoppingCart;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ITEM_ID", nullable=false, foreignKey = @ForeignKey(name = "FK_due"))
	private Item item;
/*
	@OneToMany(mappedBy="shoppingCartItem", cascade=CascadeType.ALL, orphanRemoval=true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<ShoppingCartItemSupplement> shoppingCartItemSupplement;
*/	
	public ShoppingCartItem(ShoppingCart shoppingCart, Item item) {
		super();
		this.shoppingCart = shoppingCart;
		this.item = item;
	}
	
	
	
	
	

}
