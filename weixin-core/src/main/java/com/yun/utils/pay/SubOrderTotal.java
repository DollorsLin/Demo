package com.yun.utils.pay;

import lombok.Data;

@Data
public class SubOrderTotal {
	
	private String mid;
	private String totalAmount;


	public SubOrderTotal(String mid, String totalAmount) {
		super();
		this.mid = mid;
		this.totalAmount = totalAmount;
	}
	public SubOrderTotal() {
		super();
	}
	
	
}
