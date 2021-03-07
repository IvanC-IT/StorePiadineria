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
@Table(name="PAYMENT")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Payment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PAYMENT_ID")
	private Long paymentId;
	
	@Column(name="PAYMENT_TYPE", unique=true, nullable=false, length=50)
	private String paymentType;

}
