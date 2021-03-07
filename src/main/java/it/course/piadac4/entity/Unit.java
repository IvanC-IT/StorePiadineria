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
@Table(name="UNIT")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Unit {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="UNIT_ID")
	private Long unitId;
	
	@Column(name="UNIT_MEASURE", unique=true, nullable=false, length=10)
	private String unitMeasure;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unitId == null) ? 0 : unitId.hashCode());
		result = prime * result + ((unitMeasure == null) ? 0 : unitMeasure.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Unit))
			return false;
		Unit other = (Unit) obj;
		if (unitId == null) {
			if (other.unitId != null)
				return false;
		} else if (!unitId.equals(other.unitId))
			return false;
		if (unitMeasure == null) {
			if (other.unitMeasure != null)
				return false;
		} else if (!unitMeasure.equals(other.unitMeasure))
			return false;
		return true;
	}
	
	

}
