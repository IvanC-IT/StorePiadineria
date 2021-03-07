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
@Table(name="STORE")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Store {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="STORE_ID")
	private Long storeId;
	
	@Column(name="STORE_NAME", unique=true, nullable=false, length=120)
	private String storeName;
	
	@Column(name="STORE_ADDRESS", nullable=false, length=100)
	private String storeAddress;
	
	@Column(name="STORE_CITY", nullable=false, length=60)
	private String storeCity;

	@Column(name="STORE_EMAIL", unique=true, nullable=false, length=120)
	private String storeEmail;

	@Column(name="STORE_PHONE", unique=true, nullable=false, length=100)
	private String storePhone;
	
	@Column(name="IS_OPEN", nullable=false)
	private boolean open = true;
	
}
