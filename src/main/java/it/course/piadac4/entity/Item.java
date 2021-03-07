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
@Table(name="ITEM")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Item {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ITEM_ID")
	private Long itemId;
	
	@Column(name="ITEM_NAME", unique=true, nullable=false, length=50)
	private String itemName;
	
	@Column(name="IS_VISIBLE", nullable=false)
	private boolean visible = false;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CATEGORY_ID", nullable=false)
	private Category category;

}
