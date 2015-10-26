package com.example.anf.entity;

import java.util.List;

public class PromoData {
	boolean dataFetched;
	List<Promotion> promotions;
	
	public boolean isDataFetched() {
		return dataFetched;
	}
	public void setDataFetched(boolean dataFetched) {
		this.dataFetched = dataFetched;
	}
	public List<Promotion> getPromotions() {
		return promotions;
	}
	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}
}
