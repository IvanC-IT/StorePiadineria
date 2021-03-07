package it.course.piadac4.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.course.piadac4.entity.Authority;
import it.course.piadac4.entity.AuthorityName;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{

	Optional<Authority> findByName(AuthorityName name);
	
	Set<Authority> findByNameIn(Set<AuthorityName> authorityNames);
	
	@Query(value = "SELECT u.authorities " +
			"FROM User u WHERE u.id = :id")
	Authority findRoleById(@Param("id") long id);
	

}
