package entity;

import java.util.List;

public class TransactionModel {
	
	private List<ItemEntity> itemList;
	private TransactionEntity transaction;
	private String errorMessage;
	private Long total;
	private List<String> restDenom;
	public List<ItemEntity> getItemList() {
		return itemList;
	}
	public void setItemList(List<ItemEntity> itemList) {
		this.itemList = itemList;
	}
	public TransactionEntity getTransaction() {
		return transaction;
	}
	public void setTransaction(TransactionEntity transaction) {
		this.transaction = transaction;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<String> getRestDenom() {
		return restDenom;
	}
	public void setRestDenom(List<String> restDenom) {
		this.restDenom = restDenom;
	}

}
