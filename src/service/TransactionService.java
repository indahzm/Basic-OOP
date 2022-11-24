package service;

import entity.TransactionEntity;
import entity.TransactionModel;

public interface TransactionService {
	TransactionEntity saveTransaction(Long money);
	
	TransactionModel prosesTransaction(TransactionEntity transaction, String[] items);
}
