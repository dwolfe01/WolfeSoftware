package com.wolfesoftware.BankingDemo.api;

import java.math.BigDecimal;

import com.wolfesoftware.BankingDemo.entities.BankAccount;

public interface BankAccountRepository {
	
	void createCredit(int fromBankAccount, int toBankAccount, BigDecimal bigDecimal) throws BankingDemoException;
	
	String showDatabaseStats(BankAccount bankAccount);

}
