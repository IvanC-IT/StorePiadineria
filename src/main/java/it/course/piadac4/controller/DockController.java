package it.course.piadac4.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.course.piadac4.entity.DockIngredient;
import it.course.piadac4.entity.DockIngredientId;
import it.course.piadac4.entity.Ingredient;
import it.course.piadac4.entity.Store;
import it.course.piadac4.entity.Unit;
import it.course.piadac4.payload.request.DockAddMultipleIngredientRequest;
import it.course.piadac4.payload.request.DockAddOneIngredientRequest;
import it.course.piadac4.payload.request.DockIngredientRequest;
import it.course.piadac4.payload.request.StockIngredientRequest;
import it.course.piadac4.payload.response.ApiResponseCustom;
import it.course.piadac4.payload.response.DockIngredientAddMultipleResponse;
import it.course.piadac4.payload.response.DockIngredientErrorResponse;
import it.course.piadac4.payload.response.ResponseEntityHandler;
import it.course.piadac4.repository.DockIngredientRepository;
import it.course.piadac4.repository.IngredientRepository;
import it.course.piadac4.repository.StoreRepository;
import it.course.piadac4.repository.UnitRepository;
import it.course.piadac4.service.DockIngredientService;

@RestController
@Validated
public class DockController {
	
	@Autowired DockIngredientRepository dockIngredientRepository;
	
	@Autowired UnitRepository unitRepository;
	@Autowired StoreRepository storeRepository;
	@Autowired DockIngredientService dockIngredientService;
	
	int count;
	String ord;
	String msg;
	
//	// inserimento MASSIVO ingredienti in magazzino - Utente autorizzato ROLE_ADMIN
//	@PostMapping("private/add-dock-ingredients-enrico/{storeId}")
//	@PreAuthorize("hasRole('ADMIN')")
//	@Transactional(rollbackOn=UnexpectedRollbackException.class)
//	public ResponseEntity<ApiResponseCustom> addDockIngredients(
//			@RequestBody List<StockIngredientRequest> stockIngredientRequestList, 
//			@PathVariable long storeId,
//			HttpServletRequest request) {
//		
//		ResponseEntityHandler response = new ResponseEntityHandler(request);
//		
//		for (StockIngredientRequest stockIngredientRequest : stockIngredientRequestList) {
//			
//			try {
//				dockIngredientRepository.insertDockIngredient(stockIngredientRequest.getIngredientId(),stockIngredientRequest.getQty(),  storeId);;
//			} catch(Exception e) {
//				if(!ingredientRepository.existsById(stockIngredientRequest.getIngredientId())) {
//					response.setMsg("Ingredient id "+stockIngredientRequest.getIngredientId()+" does not exists");
//					response.setStatus(HttpStatus.NOT_FOUND);
//					//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					return response.getResponseEntity();
//				}
//				
//				
//				if(!storeRepository.existsById(storeId)) {
//					response.setMsg("Store id "+storeId+" does not exists");
//					response.setStatus(HttpStatus.NOT_FOUND);
//					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					return response.getResponseEntity();
//				}
//			}
//		}
//		
//		response.setMsg("All items added");
//		return response.getResponseEntity();
//	}
//	
//	@PostMapping("private/add-ingredient-in-dock-davide/{storeId}")
//	@PreAuthorize("hasRole('ADMIN')")
//	public ResponseEntity<ApiResponseCustom> addIngredientInDock(HttpServletRequest request, 
//			@PathVariable @NotNull long storeId,
//			@RequestBody @Valid DockAddMultipleIngredientRequest dockMultipleRequest) {
//		
//		ResponseEntityHandler response = new ResponseEntityHandler(request);
//		Optional<Store> storeOptional = storeRepository.findById(storeId);
//		
//		// Controllo store
//		if(!storeOptional.isPresent()) {
//			response.setMsg("Store not found");
//			response.setStatus(HttpStatus.NOT_FOUND);
//			return response.getResponseEntity();
//		}
//		
//		DockIngredientAddMultipleResponse dockResponse = new DockIngredientAddMultipleResponse();
//		
//		for(int i = 0; i < dockMultipleRequest.getIngredients().size(); i++) {
//			DockAddOneIngredientRequest dockRequest = dockMultipleRequest.getIngredients().get(i);
//			DockIngredientErrorResponse dockErrorResponse = new DockIngredientErrorResponse();
//			
//			String ingredientName = dockRequest.getIngredientName();
//			Optional<Ingredient> ingredientOptional = ingredientRepository.findByIngredientName(ingredientName);
//			
//			dockErrorResponse.setIngredientName(ingredientName);
//			
//			boolean check = true;
//			
//			// Controllo ingredient
//			if(!ingredientOptional.isPresent()) {
//				dockErrorResponse.setMessage("The ingredient " + ingredientName + ", isn't in database. Create it before add it in dock. ");
//				check = false;
//			}
//			
//			if(check) {
//				String unitMeasure = dockRequest.getUnitMeasure();
//				Optional<Unit> unitOptional = unitRepository.findByUnitMeasure(unitMeasure);
//				
//				// Controllo unit
//				if(!unitOptional.isPresent()) {
//					dockErrorResponse.setMessage("The unit " + unitMeasure + ", isn't in database. Create it before add it in dock. ");
//					check = false;
//				}
//				
//				if(check) {
//					Unit unit = unitOptional.get();
//					Store store = storeOptional.get();
//					Ingredient ingredient = ingredientOptional.get();
//					DockIngredientId dockIngredientId = new DockIngredientId(store, ingredient);
//					Optional<DockIngredient> dockIngredientOptional = dockIngredientRepository.findById(dockIngredientId);
//					
//					int qty = dockRequest.getQty();
//					DockIngredient dockIngredient;
//					
//					// Controllo DockIngredient
//					if(dockIngredientOptional.isPresent()) {
//						dockIngredient = dockIngredientOptional.get();
//						Unit dockUnit = dockIngredient.getUnit();
//						
//						// Controllo unit e DockIngredient unit se uguali
//						if(dockUnit.equals(unit)) {
//							dockIngredient.setQty(dockIngredient.getQty() + qty);
//						
//						} else {
//							dockErrorResponse.setMessage("The unit measure " + unit.getUnitMeasure() + " is not compatible with " + dockUnit.getUnitMeasure());
//							check = false;
//						}
//					
//					// Se dockIngredient non esiste
//					} else {
//						dockIngredient = new DockIngredient(dockIngredientId, qty, unit);
//					}
//					
//					if(check) {
//						dockIngredientRepository.save(dockIngredient);
//						dockErrorResponse.setMessage("Ingredient added successfully");
//						dockResponse.getAdded().add(dockErrorResponse);
//					}
//				}	
//			}
//			
//			if(!check) {
//				dockResponse.getNotAdded().add(dockErrorResponse);
//			}
//		}
//		
//		response.setMsg(dockResponse);
//		
//		return response.getResponseEntity();
//	}
//	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("private/add_ingredients/")
	public ResponseEntity<ApiResponseCustom> add_ingredients(
			@RequestBody @Valid @NotEmpty List<DockIngredientRequest> dockRequestList,
			HttpServletRequest request) {
		
		ResponseEntityHandler response = new ResponseEntityHandler(request);
		List<String> resp = new ArrayList<>();// Stocks List response messages
		resp.clear();
		count = 0;
		dockRequestList.stream().forEach(elem -> {// iterate stocks element
			count++;// stocks counter
			try { // Get values and check It
				if (!(elem.getQtyId() > 0)) {
					ord = "Stocks " + count + " : " + "Stocks not added! Quantity must be greater than zero";
					resp.add(ord);
				}else if (!(elem.getIngredientId() > 0)){
					ord = "Stocks " + count + " : " + "Stocks not added! Ingredient not exsist, check value.";
					resp.add(ord);
				}else if (!(elem.getStoreId() > 0)){
					ord = "Stocks " + count + " : " + "Stocks not added! Store not exsist, check value.";
					resp.add(ord);
				}else {//call service for query statment
					msg = dockIngredientService.dockIngredientCheck(elem.getQtyId(), elem.getIngredientId(),
							elem.getStoreId());
					ord = "Stocks " + count + " : ";
					resp.add(ord + msg);
				}
			} catch (NullPointerException e) {//if value is null
				ord = "Stocks " + count + " : " + "Values cannot be empty. Check values.";
				resp.add(ord);
			}

		});

		response.setMsg(resp);
		return response.getResponseEntity();
	}

	

}
