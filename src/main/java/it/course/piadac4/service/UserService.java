package it.course.piadac4.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.course.piadac4.entity.Authority;
import it.course.piadac4.entity.AuthorityName;
import it.course.piadac4.entity.ShoppingCart;
import it.course.piadac4.entity.User;
import it.course.piadac4.payload.response.ResponseEntityHandler;
import it.course.piadac4.payload.response.UserResponse;
import it.course.piadac4.repository.AuthorityRepository;
import it.course.piadac4.repository.ShoppingCartRepository;
import it.course.piadac4.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AuthorityRepository authorityRepository;
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	ShoppingCartRepository shoppingCartRepository;

	public User getAuthenticatedUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		List<String> auts = authentication.getAuthorities().stream().map(a -> a.getAuthority())
				.collect(Collectors.toList());
		if (!auts.contains("ROLE_ANONYMOUS")) {
			User u = userRepository.findByUsername(authentication.getName()).get();
			return u;
		} else {
			return null;
		}

	}

	public void setAuthority(long countUsers, User user) {

		Optional<Authority> userAuthority = Optional.empty();

		if (countUsers > 0)
			userAuthority = authorityRepository.findByName(AuthorityName.ROLE_CLIENT);
		else
			userAuthority = authorityRepository.findByName(AuthorityName.ROLE_ADMIN);

		user.setAuthorities(Collections.singleton(userAuthority.get()));
	}

	public byte[] getSHA(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	public String toHexString(byte[] hash) {

		BigInteger number = new BigInteger(1, hash);
		StringBuilder hexString = new StringBuilder(number.toString(16));

		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}

		return hexString.toString().toUpperCase();

	}

	public Date adjustDate(Date date) {
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		Date correctDate = Date.from(localDateTime.atZone(ZoneOffset.UTC).toInstant());

		return correctDate;
	}

	public boolean compareTwoUser(User user1, User user2, ResponseEntityHandler response) {
		if ((user1.getId() == user2.getId())) {
			response.setMsg("IDENTICAL USER");
			response.setStatus(HttpStatus.FORBIDDEN);
			return true;
		}
		return false;
	}

	public void createShoppingCart() {

		if (!shoppingCartRepository.existsByUserAndCheckoutFalse(this.getAuthenticatedUser()))
			shoppingCartRepository.save(new ShoppingCart(this.getAuthenticatedUser()));
	}

	public List<Tuple> users1() {
		return entityManager.createQuery(
				"" + "select u.id as id, u.username as username " + "from User u " + "where u.enabled=:abilitato",
				Tuple.class).setParameter("abilitato", true).getResultList();
	}

	public List<UserResponse> users2() {
		return entityManager
				.createQuery("" + "select "
						+ "new it.course.piadac4.payload.response.UserResponse(u.id as id, u.username as username) "
						+ "from User u " + "where u.enabled=:abilitato", UserResponse.class)
				.setParameter("abilitato", true).getResultList();
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * public List<Tuple> users3(){ return entityManager.createNativeQuery("" +
	 * "select " + "u.id as id, u.username as username " + "from user u " +
	 * "where u.is_enabled=true", Tuple.class) .unwrap(
	 * org.hibernate.query.Query.class ) .setResultTransformer( new
	 * ResultTransformer() {
	 * 
	 * @Override public Object transformTuple( Object[] tuple, String[] aliases) {
	 * return new UserResponse( (BigInteger) tuple[0], (String) tuple[1] ); }
	 * 
	 * @Override public List transformList(List collection) { return collection; } }
	 * ) .getResultList(); }
	 */
}
