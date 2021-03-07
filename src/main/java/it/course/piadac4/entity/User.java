package it.course.piadac4.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import it.course.piadac4.entity.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="USER")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class User extends DateAudit{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@NaturalId(mutable=true)
	@Column(name="EMAIL", nullable=false, length=120)
	private String email;
	
	@Column(name="USERNAME", unique=true, nullable=false, length=12)
	private String username;
	
	@Column(name="PASSWORD", nullable=false, length=100)
	private String password;
	
	@Column(name="IS_ENABLED", nullable=false, columnDefinition="TINYINT(1)")
	private Boolean enabled = true;
	
	@Column(name="FIRSTNAME", nullable=false, length=100)
	private String firstname;
	
	@Column(name="LASTNAME", nullable=false, length=100)
	private String lastname;
	
	@Column(name="TELEPHONE", nullable=false, length=100)
	private String telephone;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="USER_AUTHORITIES",
		joinColumns = {@JoinColumn(name="USER_ID", referencedColumnName="ID")},
		inverseJoinColumns = {@JoinColumn(name="AUTHORITY_ID", referencedColumnName="ID")})
	private Set<Authority> authorities;

	public User(String email, String username, String password, String telephone, String firstname, String lastname) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.telephone = telephone;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
}
