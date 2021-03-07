package it.course.piadac4.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class StoreResponse {
	
	private String storeName;
	private String storeAddress;
	private String storeCity;
	private String storeEmail;
	private String storePhone;

}
