package com.wolfesoftware.BankingDemo.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BankAccountTransaction {

	public enum ACTION {DEBIT, CREDIT};
	
	@Id
	@GeneratedValue
	int id;
	
	BigDecimal amount;
	
	@ManyToOne
	@JoinColumn(name="owningBankAccountId", nullable=false)
	BankAccount bankAccount;
	
	@ManyToOne
	@JoinColumn(name="fromBankAccountId", nullable=true)
	BankAccount fromBankAccount;
	
	ACTION act;
	
	public BankAccountTransaction() {
		
	}
	
	public BankAccountTransaction(BigDecimal amount, ACTION act, BankAccount fromBankAccount) {
		this.amount = amount;
		this.act = act;
		this.fromBankAccount = fromBankAccount;
	}
	
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		switch(act) {
		case DEBIT : 
			s.append("Id: " + id + " " + act + " Amount: " + amount + " to Account: " + fromBankAccount.id);
			break;
		case CREDIT:
			s.append("Id: " + id + " " + act + " Amount: " + amount + " from Account: " + fromBankAccount.id);
			break;
		}
		return s.toString();
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	
}
