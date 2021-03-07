package it.course.piadac4.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.course.piadac4.entity.Authority;
import it.course.piadac4.entity.Order;
import it.course.piadac4.entity.OrderStatus;
import it.course.piadac4.entity.User;
import it.course.piadac4.payload.response.ApiResponseCustom;
import it.course.piadac4.payload.response.ItemOrderResponse;
import it.course.piadac4.payload.response.OrderDetailResponse;
import it.course.piadac4.payload.response.ResponseEntityHandler;
import it.course.piadac4.payload.response.StoreResponse;
import it.course.piadac4.payload.response.UserOrderResponse;
import it.course.piadac4.repository.AuthorityRepository;
import it.course.piadac4.repository.OrderRepository;
import it.course.piadac4.repository.ShoppingCartItemRepository;
import it.course.piadac4.repository.ShoppingCartItemSupplementRepository;
import it.course.piadac4.repository.StoreRepository;
import it.course.piadac4.repository.UserRepository;

@RestController
public class OrderController {
	
	@Autowired StoreRepository storeRepository;
	@Autowired UserRepository userRepository;
	@Autowired OrderRepository orderRepository;
	@Autowired ShoppingCartItemRepository shoppingCartItemRepository;
	@Autowired ShoppingCartItemSupplementRepository shoppingCartItemSupplementRepository;
	@Autowired AuthorityRepository authorityRepository;
	
	@GetMapping("private/get-order-detail/{orderId}")
	public ResponseEntity<ApiResponseCustom> getOrderDetail(@PathVariable long orderId,
			HttpServletRequest request) {
		
		Object msg;
		HttpStatus status;
		ResponseEntityHandler response;
		
		OrderDetailResponse odr = new OrderDetailResponse();
		StoreResponse store = storeRepository.getStoreByOrder(orderId);
		UserOrderResponse uor = userRepository.getUserByOrder(orderId);
		Order o = orderRepository.findById(orderId).get();
		double totalAmountOrder = 0.0d;
		
		List<ItemOrderResponse> items = shoppingCartItemRepository.getItemsByOrder(orderId);
		for(ItemOrderResponse ior : items) {
			ior.setItemSupplements(shoppingCartItemSupplementRepository.getItemSupplementsByOrder(ior.getShoppinCartItem(), o.getOrderDate() ));
			totalAmountOrder += ior.getPrice();
		}
		
		odr.setStore(store);
		odr.setUser(uor);
		odr.setOrderId(orderId);
		odr.setOrderStatus(o.getOrderStatus().getOrderStatusName());
		odr.setPaymentType(o.getPayment().getPaymentType());
		odr.setOrderDate(o.getOrderDate());
		odr.setOrderNotes(o.getOrderNotes());
		odr.setItems(items);
		odr.setAmount(totalAmountOrder);
		
		msg = odr;
		status = HttpStatus.CREATED;
		response = new ResponseEntityHandler(msg, request, status);
		return response.getResponseEntity();
		
	}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("private/change-status/{orderId}")
	public ResponseEntity<ApiResponseCustom> changeStatus(@PathVariable long orderId, @RequestParam long newStatus,
			HttpServletRequest request) {
		
		// ROLE_TOTEM
		// created, (cancelled),  payed, delivered -> payment type: all
		
		// ROLE_CLIENT
		// created, payed, (cancelled), delivering, delivered -> payment type : all except cash
		// created, (cancelled), delivering, payed, delivered -> payment type : cash
		
		// Gli ingredienti utilizzati vengono reinseriti in magazzino solo se l'ordine viene cancellato

		OrderStatus ordS = new OrderStatus(newStatus);
		Order o = orderRepository.findById(orderId).get();
		if(o == null) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			ResponseEntityHandler response = new ResponseEntityHandler("Order not found ! ", request, status); 
			return response.getResponseEntity();
		}
		Optional<User> u = userRepository.findById(o.getShoppingCart().getUser().getId());
		Authority authority = authorityRepository.findRoleById(u.get().getId());
		String[] role = authority.getName().toString().split("_"); // authorities name
		Long ordStatusId = o.getOrderStatus().getOrderStatusId(); // Order status
		Long payId = o.getPayment().getPaymentId(); // type payment
		HttpStatus status = HttpStatus.OK;
		ResponseEntityHandler response = new ResponseEntityHandler("Status successfully updated ! ", request, status);

		if (role[1].equals("CLIENT") && ordStatusId == 1 && payId == 1 && (newStatus ==3 || newStatus == 5)) { // client and created and cash true (delivering- delete)
			o.setOrderStatus(ordS);
		} else if (role[1].equals("CLIENT") && ordStatusId == 3 && payId == 1 && newStatus == 2) { // client and delivering and cash true (payed) // / // cash
			o.setOrderStatus(ordS);
		} else if (role[1].equals("CLIENT") && ordStatusId == 2 && payId == 1 && newStatus == 4) { // client and payed and cash true (delivered) // // cash
			o.setOrderStatus(ordS);
		} else if (role[1].equals("CLIENT") && ordStatusId == 1 && payId !=1 && (newStatus == 2 || newStatus ==5 )){ // client and created and !cash true (delete -Payed)
			o.setOrderStatus(ordS);
		} else if (role[1].equals("CLIENT") && (ordStatusId == 2) && payId !=1 && (newStatus == 3 || newStatus == 5)) { // if client and payed and !cash true (delivering - delete )
			o.setOrderStatus(ordS);
		}else if (role[1].equals("TOTEM") && (ordStatusId == 1) && (newStatus == 2 || newStatus ==5 ) ) { // if totem and created true (payed - cancel)
			o.setOrderStatus(ordS);
		}else if (role[1].equals("TOTEM") && (ordStatusId == 2) && newStatus == 3 ) { // if totem and payed true (delivered)
			o.setOrderStatus(ordS);
		} else if (role[1].equals("CLIENT") && (ordStatusId == 3) && payId != 1 && newStatus == 4) { // if client and delivering and !cash true (delivered)
			o.setOrderStatus(ordS);
		} else {
			status = HttpStatus.FORBIDDEN;
			response = new ResponseEntityHandler("This operation is not allowed !", request, status);
			return response.getResponseEntity();
		}
		return response.getResponseEntity();

	}

	
	@GetMapping("private/is-payed")
	@PreAuthorize("hasRole('CLIENT') or hasRole('TOTEM')")
	public ResponseEntity<ApiResponseCustom> isPayed(HttpServletRequest request) throws URISyntaxException {
		
		Object msg;
		HttpStatus status = HttpStatus.OK;
		
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8091/return-is-payed";
		URI uri = new URI(baseUrl);
		
		try {
			msg = restTemplate.getForObject(uri, String.class);
		} catch (Exception e) {
			msg = "NOT PAYED";
			status = HttpStatus.EXPECTATION_FAILED;
			e.getLocalizedMessage();
		}
		
		ResponseEntityHandler response = new ResponseEntityHandler(msg, request, status);
		return response.getResponseEntity();
		
	}
	
	
	
	

}
