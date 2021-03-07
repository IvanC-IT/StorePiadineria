package it.course.piadac4.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import it.course.piadac4.entity.Item;
import it.course.piadac4.entity.ShoppingCart;
import it.course.piadac4.entity.ShoppingCartItem;
import it.course.piadac4.payload.response.ApiResponseCustom;
import it.course.piadac4.payload.response.IngredientItemShoppingCartResponse;
import it.course.piadac4.payload.response.ResponseEntityHandler;
import it.course.piadac4.repository.ShoppingCartRepository;
import it.course.piadac4.repository.StoreRepository;
import it.course.piadac4.repository.IngredientRepository;
import it.course.piadac4.repository.ItemRepository;
import it.course.piadac4.repository.ShoppingCartItemRepository;
import it.course.piadac4.service.UserService;

@RestController
public class ShoppingCartItemController {
	
	@Autowired UserService userService;
	@Autowired ShoppingCartRepository shoppingCartRepository;
	@Autowired ShoppingCartItemRepository shoppingCartItemRepository;
	@Autowired ItemRepository itemRepository;
	@Autowired StoreRepository storeRepository;
	@Autowired IngredientRepository ingredientRepository;
	
	@PostMapping("private/add-shopping-cart-item/{itemId}/{storeId}")
	@PreAuthorize("hasRole('CLIENT') or hasRole('TOTEM')")
	@Transactional
	public ResponseEntity<ApiResponseCustom> addShoppingCartItemId(
			@PathVariable long itemId, @PathVariable long storeId, HttpServletRequest request) {

		Object msg;
		HttpStatus status;
		ResponseEntityHandler response;
		
		// TROVARE CARRELLO CON CHECKOUT A FALSE
		userService.createShoppingCart();
		Optional<ShoppingCart> sc = shoppingCartRepository.findByUserAndCheckoutFalse(userService.getAuthenticatedUser());
		if(sc.get().getStore() == null) 
			sc.get().setStore(storeRepository.findById(storeId).get());
		
		Optional<Item> i = itemRepository.findById(itemId);
		if(!i.isPresent()) {
			status = HttpStatus.NOT_FOUND;
			msg = "Item not found";
			response = new ResponseEntityHandler(msg, request, status);
			return response.getResponseEntity();
		}
		
		shoppingCartItemRepository.save(new ShoppingCartItem(sc.get(), i.get())); // INSERT ITEM (UNO AD UNO) 
		
		//ingredientRepository.findItemByPiece(itemId, sc.get().getShoppingCartId());
		// sottrazione dal magazzino degli ingredienti appena inseriti
		
		List <IngredientItemShoppingCartResponse> list = ingredientRepository.findItemByPiece(sc.get().getShoppingCartId());
		
		list.stream().forEach(elem -> {// iterate stocks element
			if (elem.getIngrId()!=0) {
		        System.out.println(elem.getIngrId());
			}	System.out.println(elem.getPiece());
		});
//		// ingredient reso/i not visible se uno di essi non è più sufficiente per produrre un item
		
		
		
		
		
		
		
		
		
		msg = "Item added";
		status = HttpStatus.CREATED;
		response = new ResponseEntityHandler(msg, request, status);
		return response.getResponseEntity();
		
	}
	
	@PostMapping("private/remove-shopping-cart-item/{shoppingCartItemId}")
	@PreAuthorize("hasRole('CLIENT') or hasRole('TOTEM')")
	public ResponseEntity<ApiResponseCustom> removeShoppingCartItemId(
			@PathVariable long shoppingCartItemId,  HttpServletRequest request) {

		Object msg;
		HttpStatus status;
		ResponseEntityHandler response;
		shoppingCartItemRepository.deleteById(shoppingCartItemId); // delete ITEM (UNO AD UNO) 
		
		// rimpiazzo della merce 
		// imposto cancelled order status
		
		
		msg = "Item removed";
		status = HttpStatus.OK;
		response = new ResponseEntityHandler(msg, request, status);
		return response.getResponseEntity();
		
	}

}
