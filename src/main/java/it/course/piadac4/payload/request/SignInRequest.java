package it.course.piadac4.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @AllArgsConstructor @NoArgsConstructor
public class SignInRequest {
	
	@Size(max=120, min=3, message="Username length must be between 3 and 12 chars")
	@NotBlank(message="User or Email must not be blank")
	private String usernameOrEmail;
	
	@Size(min=5, max=15, message="Password length must be between 3 and 12 chars")
	@NotBlank
	private String password;

}
