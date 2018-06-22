package com.wolfesoftware.BankingDemo.api;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.wolfesoftware.BankingDemo.entities.BankAccount;
import com.wolfesoftware.BankingDemo.entities.BankAccountTransaction;
import com.wolfesoftware.BankingDemo.entities.BankAccountTransaction.ACTION;

@Repository
public class BankAccountRepositoryImpl implements BankAccountRepository {

	@PersistenceContext
	EntityManager em;
	static volatile int callTimes=0;

	@Override
	@Transactional(rollbackOn=Exception.class)
	public void createCredit(int fromBankAccount, int toBankAccount, BigDecimal amount) throws BankingDemoException {
			BankAccount from = em.find(BankAccount.class, fromBankAccount);
			BankAccount to = em.find(BankAccount.class, toBankAccount);
			boolean enoughMoneyInAccount = from.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
			if (enoughMoneyInAccount) {
				from.setBalance(from.getBalance().subtract(amount));
				to.setBalance(to.getBalance().add(amount));
				from.addTransaction(new BankAccountTransaction(amount, ACTION.DEBIT, to));
				to.addTransaction(new BankAccountTransaction(amount, ACTION.CREDIT, from));
			} else {
				throw new BankingDemoException("Not enough money in account: " + fromBankAccount);
			}
	}

	@Override
	@Transactional
	public String showDatabaseStats(BankAccount bankAccount) {
		String databaseStats = "";
		Query query = em.createQuery("select count(*) from BankAccountTransaction where bankAccount=:id");
		query.setParameter("id", bankAccount);
		databaseStats += "Transaction Count: " + query.getSingleResult();
		query = em.createQuery("select sum(amount) from BankAccountTransaction where bankAccount=:id AND act=0");
		query.setParameter("id", bankAccount);
		databaseStats += "Total debits: " + query.getSingleResult();
		query = em.createQuery("select sum(amount) from BankAccountTransaction where bankAccount=:id AND act=1");
		query.setParameter("id", bankAccount);
		databaseStats += " Total credits: " + query.getSingleResult();
		return databaseStats;
	}

}
