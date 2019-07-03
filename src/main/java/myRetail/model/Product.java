package myRetail.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
	
	private int id;
	
	private String name;

	@JsonProperty("current_price")
	private Price price;
	
	public Product(int id, String name, Price price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
	
}
