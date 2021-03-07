package it.course.piadac4.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="ORDER_STATUS")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class OrderStatus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ORDER_STATUS_ID")
	private Long orderStatusId;
	
	@Column(name="ORDER_STATUS_NAME", unique=true, nullable=false, length=50)
	private String orderStatusName;

	public OrderStatus(Long orderStatusId) {
		super();
		this.orderStatusId = orderStatusId;
	}

}
