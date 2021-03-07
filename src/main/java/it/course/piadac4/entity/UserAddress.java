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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="USER_ADDRESS")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserAddress {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ADDRESS_ID")
	private Long userAddressId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable=false)
	private User user;
	
	@Column(name="USER_ADDRESS", nullable=false, length=100)
	private String userAddress;
	
	@Column(name="USER_CITY", nullable=false, length=60)
	private String userCity;
	
	@Column(name="IS_DEFAULT_ADDRESS", nullable=false)
	private boolean defaultAddress;

}
