package com.mahouttest.domain.model;


import lombok.Data;

@Data
public class StockData {
	
	
	private double rate;
	StockInfo stockInfo;
	UserInfo userInfo;
}
