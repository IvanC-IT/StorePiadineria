package it.course.piadac4.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ItemOrderResponse {
	
	
	private long shoppinCartItem;
	private String itemName;
	private List<ItemSupplementResponse> itemSupplements;
	private double price;
	
	public ItemOrderResponse(long shoppinCartItem, String itemName, double price) {
		super();
		this.shoppinCartItem = shoppinCartItem;
		this.itemName = itemName;
		this.price = price;
	}
	
	
	
}
