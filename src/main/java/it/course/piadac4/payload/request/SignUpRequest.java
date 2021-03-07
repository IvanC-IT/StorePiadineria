package it.course.piadac4.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @AllArgsConstructor @NoArgsConstructor
public class SignUpRequest {
	
	@Email
	@Size(max=120, min=6)
	@NotBlank @NotEmpty
	private String email;
	
	@Size(max=12, min=3)
	@NotBlank @NotEmpty
	@Getter
	private String username;
	
	@Size(min=5, max=15)
	@NotBlank @NotEmpty
	@Getter
	private String password;
	
	@Size(max=100, min=2)
	@NotBlank @NotEmpty
	@Getter
	private String telephone;
	
	@Size(max=100, min=2)
	@NotBlank @NotEmpty
	private String firstname;
	
	@Size(max=100, min=2)
	@NotBlank @NotEmpty
	private String lastname;

	public String getEmail() {
		return email.toLowerCase();
	}

	public String getFirstname() {
		return firstname.toLowerCase();
	}

	public String getLastname() {
		return lastname.toLowerCase();
	}
}
