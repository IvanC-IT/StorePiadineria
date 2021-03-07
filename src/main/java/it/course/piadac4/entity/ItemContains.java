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
@Table(name="ITEM_CONTAINS")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ItemContains {

	@EmbeddedId
	private ItemContainsId itemContainsId;
	
	@Column(name="PIECE", nullable=false, columnDefinition="TINYINT(1)")
	private int piece;
}
