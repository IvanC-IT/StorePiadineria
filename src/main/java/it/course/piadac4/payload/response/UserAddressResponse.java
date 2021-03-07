package it.course.piadac4.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserAddressResponse {
	
	private String userAddress;
	private String userCity;

}
