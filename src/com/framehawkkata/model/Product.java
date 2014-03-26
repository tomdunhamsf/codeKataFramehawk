package com.framehawkkata.model;
/**
 * This would probably be a JPA object in a real project.
 * @author tdunham
 *
 */
public class Product {
	String name;
	public Product(String prod){
		name=prod;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
