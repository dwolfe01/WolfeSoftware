package com.wolfesoftware.BankingDemo.api;

public class BankingDemoException extends Exception {
	
	public BankingDemoException(String msg) {
		super(msg);
	}

	public BankingDemoException(Exception e) {
		super(e);
	}

}
