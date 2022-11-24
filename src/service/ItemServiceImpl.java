package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entity.ItemEntity;

public class ItemServiceImpl implements ItemService {
	
	private static List<ItemEntity> items; 
	
	public List<ItemEntity> getItems() {
		items = new ArrayList<ItemEntity>();
		ItemEntity biskuit = new ItemEntity("Biskuit", 50, 6000L);
		items.add(biskuit);
		ItemEntity chips = new ItemEntity("Chips", 50, 8000L);
		items.add(chips);
		ItemEntity oreo = new ItemEntity("Oreo", 50, 10000L);
		items.add(oreo);
		ItemEntity tango = new ItemEntity("Tango", 50, 12000L);
		items.add(tango);
		ItemEntity cokelat = new ItemEntity("Cokelat", 50, 15000L);
		items.add(cokelat);
		return items;
	}
	
	public ItemEntity findItemByName(String name) {
		List<ItemEntity> newItems = items.stream().filter(it -> it.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
		return newItems.size() > 0 ? newItems.get(0) : null;
	}
	
	public void updateItem(List<ItemEntity> itemList) {
		for (ItemEntity item : itemList) {
			Integer index = items.indexOf(items.stream().filter(i -> i.getName().equalsIgnoreCase(item.getName()))
				.collect(Collectors.toList()).get(0));
			items.set(index, item);
		}
	}
}
