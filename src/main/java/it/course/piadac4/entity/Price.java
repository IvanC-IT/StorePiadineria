package it.course.piadac4.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="PRICE")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Price {
	
	@EmbeddedId 
	private PriceId priceId;
	
	@Column(name="PRICE", nullable=false, columnDefinition="DECIMAL(5,2)")
	private double price;
	
	@Column(name="END_DATE", nullable=false, columnDefinition="TIMESTAMP")
	private Date endDate = new Date(2177449199L); // 2177449199L = 2038-12-31 23:59:59
	
	@Column(name="QTY", nullable=false, columnDefinition="INT(2)")
	private int qty;
	
	@ManyToOne
	@JoinColumn(name="UNIT_ID", nullable=false)
	private Unit unit;

	
}
