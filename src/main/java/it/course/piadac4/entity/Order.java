package it.course.piadac4.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="`ORDER`")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ORDER_ID")
	private Long orderId;
	
	@ManyToOne
	@JoinColumn(name="STORE_ID", nullable=false)
	private Store store;
	
	@ManyToOne
	@JoinColumn(name="SHOPPING_CART_ID", nullable=false)
	private ShoppingCart shoppingCart;
	
	@ManyToOne
	@JoinColumn(name="ORDER_STATUS_ID", nullable=false)
	private OrderStatus orderStatus;
	
	@ManyToOne
	@JoinColumn(name="PAYMENT_ID", nullable=false)
	private Payment payment;
	
	@ManyToOne
	@JoinColumn(name="USER_ADDRESS_ID", nullable=false)
	private UserAddress userAddress;
	
	@Column(name="NOTES", nullable=true)
	private String orderNotes;
	
	@Column(name="ORDER_DATE", 
			updatable=false, insertable=false, nullable=false,
			columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date orderDate;
	
	

}
