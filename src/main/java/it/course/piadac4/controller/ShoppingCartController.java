package it.course.piadac4.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.course.piadac4.entity.Order;
import it.course.piadac4.entity.OrderStatus;
import it.course.piadac4.entity.Payment;
import it.course.piadac4.entity.ShoppingCart;
import it.course.piadac4.entity.Store;
import it.course.piadac4.entity.User;
import it.course.piadac4.entity.UserAddress;
import it.course.piadac4.payload.request.OrderRequest;
import it.course.piadac4.payload.request.ShoppingCartItemSupplementRequest;
import it.course.piadac4.payload.response.ApiResponseCustom;
import it.course.piadac4.payload.response.ResponseEntityHandler;
import it.course.piadac4.repository.OrderRepository;
import it.course.piadac4.repository.OrderStatusRepository;
import it.course.piadac4.repository.PaymentRepository;
import it.course.piadac4.repository.ShoppingCartItemRepository;
import it.course.piadac4.repository.ShoppingCartRepository;
import it.course.piadac4.repository.StoreRepository;
import it.course.piadac4.repository.UserAddressRepository;
import it.course.piadac4.repository.UserRepository;
import it.course.piadac4.service.UserService;

@RestController
public class ShoppingCartController {
	
	@Autowired ShoppingCartRepository shoppingCartRepository;
	@Autowired UserService userService;
	@Autowired ShoppingCartItemRepository shoppingCartItemRepository;
	@Autowired StoreRepository storeRepository;
	@Autowired UserRepository userRepository;
	@Autowired UserAddressRepository userAddressRepository;
	@Autowired PaymentRepository paymentRepository;
	@Autowired OrderStatusRepository orderStatusRepository;
	@Autowired OrderRepository orderRepository;
	
	
	@PostMapping("private/checkout")
	@PreAuthorize("hasRole('CLIENT') or hasRole('TOTEM')")
	@Transactional
	public ResponseEntity<ApiResponseCustom> addShoppingCart(@RequestBody OrderRequest orderRequest,
			HttpServletRequest request) {
		
		Object msg;
		HttpStatus status;
		ResponseEntityHandler response;
		
		// cambiare status shoppingCart -> checkout
		Optional<ShoppingCart> sc = shoppingCartRepository.findByUserAndCheckoutFalse(userService.getAuthenticatedUser());
		
		if(shoppingCartItemRepository.countShoppingCartItems(sc.get().getShoppingCartId()) < 1) { //prendi lo shopping cart id
			msg = "No items in shopping cart";
			status = HttpStatus.NOT_FOUND;
			response = new ResponseEntityHandler(msg, request, status);
			return response.getResponseEntity();
			
		}
		
		// creare order
		Order o = new Order();
		
		// store
		Optional<Store> s = storeRepository.findByStoreIdAndOpenTrue(orderRequest.getStoreId());
		if(!s.isPresent()) {
			msg = "No store found";
			status = HttpStatus.NOT_FOUND;
			response = new ResponseEntityHandler(msg, request, status);
			return response.getResponseEntity();
		}
		
		// user
		User u = userService.getAuthenticatedUser();
		
		// user address
		Optional<UserAddress> ua = userAddressRepository.findById(orderRequest.getUserAddressId());
		
		// payment
		Optional<Payment> p = paymentRepository.findById(orderRequest.getPaymentId());
		
		// order status
		Optional<OrderStatus> os = orderStatusRepository.findByOrderStatusName("CREATED");
		
			
		o.setStore(s.get());
		o.setUserAddress(ua.get());
		o.setOrderStatus(os.get());
		o.setShoppingCart(sc.get());
		o.setPayment(p.get());
		o.setOrderNotes(orderRequest.getOrderNotes());
		orderRepository.save(o);
		
		sc.get().setCheckout(true);		
		
		msg = "Order created";
		status = HttpStatus.CREATED;
		response = new ResponseEntityHandler(msg, request, status);
		return response.getResponseEntity();

	}
}
