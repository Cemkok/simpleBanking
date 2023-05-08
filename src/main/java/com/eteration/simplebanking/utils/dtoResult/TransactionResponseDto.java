package com.eteration.simplebanking.utils.dtoResult;

import java.util.UUID;



import lombok.Data;

@Data
public class TransactionResponseDto extends Response {
	
	public TransactionResponseDto(String status, UUID approvalCode) {
		this.status = status;
		this.approvalCode = approvalCode;
	}
	public String status;
	public UUID approvalCode;
	


}
