package com.wolfesoftware.BankingDemo.api;

import org.springframework.data.repository.CrudRepository;

import com.wolfesoftware.BankingDemo.entities.BankAccount;

public interface BankingDemoRepository extends CrudRepository<BankAccount, Integer>, BankAccountRepository  {

}
