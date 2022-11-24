package entity;

import java.util.UUID;

public class ItemEntity {
	
	private String id;
	private String name;
	private Integer quantity;
	private Integer available;
	private Long price;
	
	public ItemEntity (String name, Integer quantity, Long price) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.quantity = quantity;
		this.setAvailable(quantity);
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	
}
