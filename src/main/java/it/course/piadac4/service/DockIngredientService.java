package it.course.piadac4.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.course.piadac4.entity.Item;
import it.course.piadac4.entity.Store;
import it.course.piadac4.repository.DockIngredientRepository;
import it.course.piadac4.repository.IngredientRepository;
import it.course.piadac4.repository.ItemRepository;
import it.course.piadac4.repository.StoreRepository;

@Service
public class DockIngredientService {
	
	@Autowired DockIngredientRepository dockIngredientRepository ;
	@Autowired StoreRepository storeRepository;
	@Autowired IngredientRepository ingredientRepository ;
	@Autowired ItemRepository itemRepository;
	
	@Transactional
	public  String  dockIngredientCheck(int qtyId, long ingredientId, long storeId) {
		
		if (!ingredientRepository.existsById(ingredientId)&& !storeRepository.existsById(storeId)){
			return "The ingredient "+ingredientId+" and Store "+storeId+"  entered do not exist ";}
		
		if (!storeRepository.existsById(storeId)) {return "Store "+storeId+" entered do not exist ";}
		
		if (!ingredientRepository.existsById(ingredientId)) {return "Ingredient "+ingredientId+" entered do not exist ";}
		
		dockIngredientRepository.insertDockIngredient(ingredientId,qtyId,storeId);
		return "Element "+ingredientId+" successfully added to the warehouse of the store "+storeId;
		
		
		
		
		
	}
}
//	@Transactional
//	public void subtractIngredientFromDock(Item item , Store store) {
//		
//		List<IngredientToSubtract> ingredientToSubtractList = ingredientRepository.findIngredientByItemId(item.getItemId());
//		ingredientToSubtractList.stream().forEach(a ->{
//			DockIngredient dockIngredient = dockIngredientRepository.findById(new DockIngredientId(store,a.getIngredient())).get();
//			dockIngredient.setQty(dockIngredient.getQty() - a.getPiece()*a.getQty());
//			});
//	
//	
//}
	
	 

	

