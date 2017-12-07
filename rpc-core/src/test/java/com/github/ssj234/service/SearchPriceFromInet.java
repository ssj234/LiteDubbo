package com.github.ssj234.service;

public class SearchPriceFromInet implements ISearchPrice {

	public String getPrice(String name) {
		return String.format("price of [%s] is $100.00", name);
	}
	
}
