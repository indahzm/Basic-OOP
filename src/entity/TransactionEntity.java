package entity;

import java.util.Set;
import java.util.UUID;

import constant.TransactionStatus;

public class TransactionEntity extends BaseEntity{
	
	private String id;
	private Long cash;
	private Long total;
	private Long change;
	private Long rest;
	private TransactionStatus status;
	private Set<TransactionDetailEntity> transactionDetails;
	
	public TransactionEntity(Long cash) {
		this.id = UUID.randomUUID().toString();
		this.cash = cash;
		this.status = TransactionStatus.PENDING;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getCash() {
		return cash;
	}
	public void setCash(Long cash) {
		this.cash = cash;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getChange() {
		return change;
	}
	public void setChange(Long change) {
		this.change = change;
	}
	public Long getRest() {
		return rest;
	}
	public void setRest(Long rest) {
		this.rest = rest;
	}
	public TransactionStatus getStatus() {
		return status;
	}
	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
	public Set<TransactionDetailEntity> getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(Set<TransactionDetailEntity> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
}
