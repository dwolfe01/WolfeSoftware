package com.wolfesoftware.BankingDemo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import com.wolfesoftware.BankingDemo.api.BankingDemoException;
import com.wolfesoftware.BankingDemo.api.BankingDemoRepository;
import com.wolfesoftware.BankingDemo.entities.BankAccount;
import com.wolfesoftware.BankingDemo.entities.BankAccountTransaction;
import com.wolfesoftware.BankingDemo.entities.BankAccountTransaction.ACTION;

public class BankAccountRespositoryStub implements BankingDemoRepository{
	
	Map<Integer, BankAccount> myMap = new HashMap<>();

	@Override
	public void createCredit(int fromBankAccount, int toBankAccount, BigDecimal amount)
			throws BankingDemoException {
		BankAccount from = this.findById(fromBankAccount).get();
		BankAccount to = this.findById(toBankAccount).get();
		boolean enoughMoneyInAccount = from.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
		if (enoughMoneyInAccount) {
			from.addTransaction(new BankAccountTransaction(amount, ACTION.DEBIT, to));
			to.addTransaction(new BankAccountTransaction(amount, ACTION.CREDIT, from));
			from.setBalance(from.getBalance().subtract(amount));
			to.setBalance(to.getBalance().add(amount));
		} else {
			throw new BankingDemoException("Not enough money in account: " + fromBankAccount);
		}
		
	}

	@Override
	public String showDatabaseStats(BankAccount bankAccount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(BankAccount arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends BankAccount> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(Integer arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<BankAccount> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<BankAccount> findAllById(Iterable<Integer> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BankAccount> findById(Integer arg0) {
		return Optional.of(myMap.get(arg0));
	}

	@Override
	public <S extends BankAccount> S save(S arg0) {
		Random random = new Random(System.nanoTime());
		int randomInt = random.nextInt(1000000000);
		arg0.setId(randomInt);
		myMap.put(arg0.getId(), arg0);
		return arg0;
	}

	@Override
	public <S extends BankAccount> Iterable<S> saveAll(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
