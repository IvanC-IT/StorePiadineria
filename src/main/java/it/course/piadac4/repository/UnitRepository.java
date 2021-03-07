package it.course.piadac4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>{
	
	Optional<Unit> findByUnitMeasure(String unitMeasure);
}
