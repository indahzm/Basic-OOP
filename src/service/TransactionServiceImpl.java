package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import constant.TransactionStatus;
import entity.ItemEntity;
import entity.TransactionDetailEntity;
import entity.TransactionEntity;
import entity.TransactionModel;

public class TransactionServiceImpl implements TransactionService {
	
	public static List<Long> denomList;

	@Override
	public TransactionEntity saveTransaction(Long money) {
		TransactionEntity transaction = new TransactionEntity(money);
		transaction.setCreatedAt(new Date());
		return transaction;
	}

	@Override
	public TransactionModel prosesTransaction(TransactionEntity transaction, String[] items) {
		ItemServiceImpl itemService = new ItemServiceImpl();
		TransactionModel transactionModel = new TransactionModel();
		List<ItemEntity> itemList = new ArrayList<ItemEntity>();
		Long total = 0L;
		Set<TransactionDetailEntity> transactionDetailList = new HashSet<TransactionDetailEntity>();
		for (String str : items) {
			String itemName = str.split("-")[0];
			Integer qty = Integer.valueOf(str.split("-")[1]);
			ItemEntity item = itemService.findItemByName(itemName);
			if (item != null) {
				total += item.getPrice()*qty;
				item.setAvailable(item.getAvailable() - qty);
				TransactionDetailEntity transactionDetail = getTransactionDetail(item, qty);
				transactionDetail.setTransaction(transaction);
				transactionDetailList.add(transactionDetail);
				itemList.add(item);
			} else {
				transactionModel.setErrorMessage("Your item is not found");
				break;
			}
		}
		
		if (total <= transaction.getCash()) {
			transaction = updateTransaction(transaction, total);
			transaction.setTransactionDetails(transactionDetailList);
			transactionModel.setTransaction(transaction);
			transactionModel = getRest(transactionModel);
			transactionModel.setItemList(itemList);
			transactionModel.setTotal(total);
			
			itemService.updateItem(transactionModel.getItemList());
		} else {
			transactionModel.setErrorMessage("Your money is not enough");
		}
		
		return transactionModel;
	}
	
	public TransactionEntity updateTransaction(TransactionEntity transaction, Long total) {
		transaction.setTotal(total);
		transaction.setChange(transaction.getCash()-transaction.getTotal());
		transaction.setStatus(TransactionStatus.SUCCESS);
		transaction.setUpdatedAt(new Date());
		return transaction;
	}
	
	public TransactionDetailEntity getTransactionDetail(ItemEntity item, Integer quantity) {
		TransactionDetailEntity transactionDetail = new TransactionDetailEntity();
		transactionDetail.setId(UUID.randomUUID().toString());
		transactionDetail.setItem(item);
		transactionDetail.setQuantity(quantity);
		transactionDetail.setTotal(item.getPrice()*quantity);
		return transactionDetail;
	}
	
	public TransactionModel getRest(TransactionModel transactionModel) {
		Long change = transactionModel.getTransaction().getChange();
		List<Long> denominations = getListDenom();
		List<String> restDenom = new ArrayList<String>();
		denominations.sort(Collections.reverseOrder());
		Map<Long, Long> denoms = new HashMap<Long, Long>();
		for (Long denom : denominations) {
			if (change >= (denom)) {
				Long count = (long) (Math.floor(change/denom));
				denoms.put(denom, count);
				change -= denom*count;
				restDenom.add(count + "*" + denom);
			}
		}
		transactionModel.setRestDenom(restDenom);
		transactionModel.getTransaction().setRest(change);
		return transactionModel;
	}
	
	public List<Long> getListDenom() {
		List<Long> denom = new ArrayList<Long>();
		denom.add(50000L);
		denom.add(20000L);
		denom.add(10000L);
		denom.add(5000L);
		denom.add(2000L);
		denomList = denom;
		return denom;
	}
	
	public boolean isDenomExist(Long money) {
		return denomList.stream().filter(dnm -> dnm.equals(money)).collect(Collectors.toList()).size() > 0;
	}
}
