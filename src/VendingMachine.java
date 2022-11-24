import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entity.ItemEntity;
import entity.TransactionEntity;
import entity.TransactionModel;
import service.ItemServiceImpl;
import service.TransactionServiceImpl;

public class VendingMachine {
	
	public static Scanner scanner = new Scanner(System.in);
	public static TransactionServiceImpl transactionService = new TransactionServiceImpl();
	public static void main(String[] args) {
		homeScreen();
		System.out.println("Do you want a buy something ? (Y/N)");
		System.out.println();
		if (scanner.nextLine().equalsIgnoreCase("y")) {
			startVendingMachine();
		} else {
			System.out.println("See you");
			System.out.println("====================================================");
		}
	}
	
	private static void startVendingMachine() {
		TransactionEntity transaction = getMoney();
		getItem(transaction);
	}
	
	private static TransactionEntity getMoney() {
		return getMoney(null);
	}
	private static TransactionEntity getMoney(TransactionEntity transaction) {
		System.out.println("Money allowed");
		System.out.println(transactionService.getListDenom());
		System.out.println();
		System.out.println("Please insert your money");		
		System.out.println();
		Long money = scanner.nextLong();
		scanner.nextLine();
		money = checkDenomination(money);
		while (money == null) {
			System.out.println();
			System.out.println("Your money is not allowed. Please insert another money");
			money = scanner.nextLong();
			scanner.nextLine();
			money = checkDenomination(money);
		}
		if (transaction != null) {
			money += transaction.getCash();
		}
		transaction = transactionService.saveTransaction(money);
		return transaction;
	}
	
	private static void getItem(TransactionEntity transaction) {
		System.out.println();
		System.out.println("Please choose the item and quantity (seperate with coma if more than 1 item, ex : Biskuit-3,Oreo-10)");
		System.out.println();
		
		String itemString = scanner.nextLine();
		String[] itemStrings = itemString.split(",");
		
		TransactionModel transactionModel = transactionService.prosesTransaction(transaction, itemStrings);
		if (transactionModel.getErrorMessage() == null) {
			transaction = transactionModel.getTransaction();
			System.out.println("Your Change is " + transaction.getChange());
			if (!transaction.getRest().equals(0L)) {
				//If denomination for change not exist
				System.out.println(transactionModel.getTransaction().getRest() + " of your money will be return by candy");
				System.out.println("Your Change will return with " + transactionModel.getRestDenom());
			}
			System.out.println("Please take your money and item, and Enjoyed it!");
			
			System.out.println();
			System.out.println("See you");
			System.out.println();
			System.out.println("====================================================");
		} else {
			System.out.println(transactionModel.getErrorMessage());
			getMenu(transaction);
		}
	}
	
	public static void getMenu(TransactionEntity transaction) {
		System.out.println("(1) Add money");
		System.out.println("(2) Change your item");
		System.out.println();
		int menu = scanner.nextInt();
		scanner.nextLine();
		if (menu == 1) {
			transaction = getMoney(transaction);
			getItem(transaction);
		} else if (menu == 2) {
			getItem(transaction);
		} else {
			
		}
	}
	public static Long checkDenomination(Long money) {
		Boolean isDenomExist = transactionService.isDenomExist(money);
		return isDenomExist ? money : null;
	}
	
	public static void homeScreen() {
		ItemServiceImpl itemService = new ItemServiceImpl();
		System.out.println("====================================================");
		System.out.println("Welcome to Vending Machine");
		System.out.println("Available Item");
		
		List<ItemEntity> items = itemService.getItems();
		System.out.println(items.stream().map(
			item -> item.getName() + " - " + item.getAvailable() + "items - @Rp" + item.getPrice() +",-")
				.collect(Collectors.joining("\n")));
	}
	
}
