package it.course.piadac4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
	

}
