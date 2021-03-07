package it.course.piadac4.entity;

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
@Table(name="DOCK_INGREDIENT")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DockIngredient {
	
	@EmbeddedId
	private DockIngredientId dockIngredientId;
	
	@Column(name="QTY", nullable=false, columnDefinition="INT(5)")
	private int qty;
	
	@ManyToOne
	@JoinColumn(name="UNIT_ID", nullable=false)
	private Unit unit;
	
	@Column(name="IS_VISIBLE", nullable=false)
	private boolean visible = true;
	
	public DockIngredient(DockIngredientId dockIngredientId, int qty, Unit unit) {
		super();
		this.dockIngredientId = dockIngredientId;
		this.qty = qty;
		this.unit = unit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dockIngredientId == null) ? 0 : dockIngredientId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DockIngredient))
			return false;
		DockIngredient other = (DockIngredient) obj;
		if (dockIngredientId == null) {
			if (other.dockIngredientId != null)
				return false;
		} else if (!dockIngredientId.equals(other.dockIngredientId))
			return false;
		return true;
	}

}
