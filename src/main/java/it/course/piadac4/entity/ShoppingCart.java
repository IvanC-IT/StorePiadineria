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

import it.course.piadac4.entity.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="SHOPPING_CART")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ShoppingCart extends DateAudit{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SHOPPING_CART_ID")
	private Long shoppingCartId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="STORE_ID", nullable=true)
	private Store store;
	
	/* false: se non si trasforma in un order, ovvero non viene effettuato il checkout; 
	 * true se viene eseguito il checkout
	 */
	@Column(name="CHECKOUT", nullable=false)
	private boolean checkout = false;

	public ShoppingCart(User user) {
		super();
		this.user = user;
	}
	
	

}
