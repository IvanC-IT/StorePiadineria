package it.course.piadac4.payload.response;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class OrderDetailResponse {
	
	// dati del negozio
	private StoreResponse store;
	
	// dati del cliente
	private UserOrderResponse user;
	
	// dati dell'ordine
	private long orderId;
	private String orderStatus;
	private String paymentType;
	private Date orderDate;
	private String orderNotes;
	
	// items contenuti nell'ordine con relativi prezzi
	private List<ItemOrderResponse> items;
	
	// totale
	private double amount;

}
