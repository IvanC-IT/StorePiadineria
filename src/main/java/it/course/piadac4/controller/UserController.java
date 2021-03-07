package it.course.piadac4.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.course.piadac4.entity.User;
import it.course.piadac4.payload.request.SignInRequest;
import it.course.piadac4.payload.request.SignUpRequest;
import it.course.piadac4.payload.response.ApiResponseCustom;
import it.course.piadac4.payload.response.ResponseEntityHandler;
import it.course.piadac4.payload.response.UserResponse;
import it.course.piadac4.repository.AuthorityRepository;
import it.course.piadac4.repository.UserRepository;
import it.course.piadac4.security.JwtAuthenticationResponse;
import it.course.piadac4.security.JwtTokenUtil;
import it.course.piadac4.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {
	
	@Autowired AuthenticationManager authenticationManager;
	@Autowired PasswordEncoder passwordEncoder;
	
	@Autowired AuthorityRepository authorityRepository;
	@Autowired UserRepository userRepository;

	@Autowired UserDetailsService userDetailsService;
	@Autowired UserService userService;
	
	@Autowired JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}") private String tokenHeader;
	
	@PostMapping("public/signup")
	@Transactional
	public ResponseEntity<ApiResponseCustom> signUp(HttpServletRequest request,
			@Valid @RequestBody SignUpRequest signUpRequest) {

		long countUsers = userRepository.count(); // select count(*) from user;
		log.info("Numero utenti presenti sul db: "+countUsers);
		log.error("ERRORE !");
		log.debug("DEBUG !");
		log.trace("TRACE ");

		Object msg;
		HttpStatus status;
		ResponseEntityHandler response;

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			msg = "User already registered";
			status = HttpStatus.OK;

		} else if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			msg = "Username already in use";
			status = HttpStatus.OK;

		} else {
			User user = new User(signUpRequest.getEmail(), signUpRequest.getUsername(), signUpRequest.getPassword(), 
					signUpRequest.getTelephone(), signUpRequest.getFirstname(), signUpRequest.getLastname());
			user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
			userRepository.save(user);
			userService.setAuthority(countUsers, user);

			msg = "User successfully registered. Please Sign In now";
			status = HttpStatus.OK;
		}

		response = new ResponseEntityHandler(msg, request, status);
		return response.getResponseEntity();
	}
	
	
	@PostMapping("public/signin")
	public ResponseEntity<ApiResponseCustom> signIn(@Valid @RequestBody SignInRequest signInRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, JsonProcessingException {

		Optional<User> user = userRepository.findByUsernameOrEmail(signInRequest.getUsernameOrEmail(),
				signInRequest.getUsernameOrEmail());

		Object msg;
		HttpStatus status;
		ResponseEntityHandler responseEntity;

		if (!user.isPresent()) {
			msg = "Please register yourself before the Sign In";
			status = HttpStatus.BAD_REQUEST;
			responseEntity = new ResponseEntityHandler(msg, request, status);
			return responseEntity.getResponseEntity();
		}
		
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.get().getUsername(), signInRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		response.setHeader(tokenHeader, token);		

		msg = new JwtAuthenticationResponse(userDetails.getUsername(), userDetails.getAuthorities(), token);
		status = HttpStatus.OK;
		
		userService.createShoppingCart();

		responseEntity = new ResponseEntityHandler(msg, request, status);
		return responseEntity.getResponseEntity();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("public/usersTuple")
	public ResponseEntity<ApiResponseCustom> usersTuple(HttpServletRequest request) {

		Object msg;
		HttpStatus status;
		ResponseEntityHandler response;
		
		List<Tuple> usersTuple = userService.users1(); 
		
		Tuple user = usersTuple.get(0);
		System.out.print("id: "+(long) user.get("id")+ " - username: " +user.get("username"));
		
		
		msg = usersTuple;
		status = HttpStatus.OK;
		response = new ResponseEntityHandler(msg, request, status);
		return response.getResponseEntity();
	}
	
	@GetMapping("public/usersResponse")
	public ResponseEntity<ApiResponseCustom> usersResponse(HttpServletRequest request) {

		Object msg;
		HttpStatus status;
		ResponseEntityHandler response;
		
		List<UserResponse> urs = userService.users2();
		
		
		msg = urs;
		status = HttpStatus.OK;
		response = new ResponseEntityHandler(msg, request, status);
		return response.getResponseEntity();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*
	@GetMapping("public/users3")
	public ResponseEntity<ApiResponseCustom> users3(HttpServletRequest request) {

		Object msg;
		HttpStatus status;
		ResponseEntityHandler response;
		
		List<Tuple> usersX = userService.users3();
		
		msg = usersX;
		status = HttpStatus.OK;
		response = new ResponseEntityHandler(msg, request, status);
		return response.getResponseEntity();
	}
*/
}
