package it.course.piadac4.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.course.piadac4.entity.ShoppingCartItemSupplement;
import it.course.piadac4.entity.ShoppingCartItemSupplementId;
import it.course.piadac4.payload.request.ShoppingCartItemSupplementRequest;
import it.course.piadac4.payload.response.ApiResponseCustom;
import it.course.piadac4.payload.response.ResponseEntityHandler;
import it.course.piadac4.repository.IngredientRepository;
import it.course.piadac4.repository.ShoppingCartItemRepository;
import it.course.piadac4.repository.ShoppingCartItemSupplementRepository;

@RestController
public class ShoppingCartItemSupplementController {
	
	@Autowired ShoppingCartItemSupplementRepository shoppingCartItemSupplementRepository;
	@Autowired ShoppingCartItemRepository shoppingCartItemRepository;
	@Autowired IngredientRepository ingredientRepository;
	
	@PostMapping("private/add-shopping-cart-item-supplement")
	@PreAuthorize("hasRole('CLIENT') or hasRole('TOTEM')")
	public ResponseEntity<ApiResponseCustom> addShoppingCartSupplementItemId(
			@RequestBody ShoppingCartItemSupplementRequest shoppingCartItemSupplementRequest, HttpServletRequest request) {

		Object msg;
		HttpStatus status;
		ResponseEntityHandler response;
		
		ShoppingCartItemSupplement scis = new ShoppingCartItemSupplement(
				new ShoppingCartItemSupplementId(
						shoppingCartItemRepository.findById(shoppingCartItemSupplementRequest.getShoppingCartItemId()).get(),
						ingredientRepository.findById(shoppingCartItemSupplementRequest.getIngredientId()).get()
						),
				shoppingCartItemSupplementRequest.getQty()
				);
		shoppingCartItemSupplementRepository.save(scis);
		
		msg = "Supplement added";
		status = HttpStatus.CREATED;
		response = new ResponseEntityHandler(msg, request, status);
		return response.getResponseEntity();
	}

}
