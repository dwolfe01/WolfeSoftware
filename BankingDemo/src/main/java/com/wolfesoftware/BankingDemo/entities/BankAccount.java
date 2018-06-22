package com.wolfesoftware.BankingDemo.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class BankAccount {
	@Id
	@GeneratedValue
	int id;
	String name;
	BigDecimal balance;
	@OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<BankAccountTransaction> transactions = new ArrayList<>();
	@Version
	@Column(name = "VERSION")
	private Integer version;

	public BankAccount() {
	}

	public BankAccount(String name, BigDecimal balance) {
		this.name = name;
		this.balance = balance;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<BankAccountTransaction> getTransactions() {
		return transactions;
	}

	public void addTransaction(BankAccountTransaction transaction) {
		transaction.setBankAccount(this);
		this.transactions.add(transaction);
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("BANK ACCOUNT: Id: " + id + " Name: " + name + " Balance: " + balance + "\n");
		for (BankAccountTransaction bat : transactions) {
			s.append("TRANSACTION: " + bat.toString() + "\n");
		}
		return s.toString();
	}
}
