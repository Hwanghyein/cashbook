package com.gdu.cashbook.vo;

public class YearAndPriceAndMemberId {
	private int year;
	private int price;
	private String memberId;
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	@Override
	public String toString() {
		return "YearAndPriceAndMemberId [year=" + year + ", price=" + price + ", memberId=" + memberId + "]";
	}
	
}
