package it.course.piadac4.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserOrderResponse {
	
	private String lastname;
	private String firstname;
	private String email;
	private String telephone;
	private String userAddress;
	private String userCity;

}
