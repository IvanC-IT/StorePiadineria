package it.course.piadac4.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.PathVariable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class OrderRequest {
	
	@NotNull
	long storeId;
	@NotNull
	long userAddressId;
	@NotNull
	long paymentId;
	String orderNotes;
	

}
