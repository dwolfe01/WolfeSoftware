package com.wolfesoftware.BankingDemo;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.wolfesoftware.BankingDemo.api.BankingDemoException;
import com.wolfesoftware.BankingDemo.api.BankingDemoRepository;
import com.wolfesoftware.BankingDemo.entities.BankAccount;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BankingDemoApplicationTest {
	
	private static final BigDecimal OPENING_BALANCE = new BigDecimal(1000);

	@Autowired
	private BankingDemoRepository repository;
	
	private BankAccount tomsBankAccount;
	private BankAccount dansBankAccount;
	private BankAccount marthasBankAccount;
	
	@Before
	public void tearup(){
		tomsBankAccount = new BankAccount("tom's bank account", OPENING_BALANCE);
		repository.save(tomsBankAccount);
		dansBankAccount = new BankAccount("dan's bank account",OPENING_BALANCE);
		repository.save(dansBankAccount);
		marthasBankAccount = new BankAccount("martha's bank account",OPENING_BALANCE);
		repository.save(marthasBankAccount);
	}


	@Test
	public void createCreditTransaction() throws BankingDemoException {
		repository.createCredit(tomsBankAccount.getId(), dansBankAccount.getId(), new BigDecimal(100));
		showDatabase();
	}

	@Test(expected=BankingDemoException.class)
	public void shouldThrowBankingDemoExceptionForNotEnoughMoney() throws BankingDemoException {
		repository.createCredit(tomsBankAccount.getId(), dansBankAccount.getId(), new BigDecimal(1000.1));
	}
	
	@Test
	public void raceToSpendMoney() throws InterruptedException, ExecutionException {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		Runnable creditMoneyThread = getCreditMoneyThread(tomsBankAccount.getId(), dansBankAccount.getId(), "tom -> dan");
		Runnable creditMoneyThread2 = getCreditMoneyThread(dansBankAccount.getId(), tomsBankAccount.getId(), "dan -> tom");
		Runnable creditMoneyThread3 = getCreditMoneyThread(marthasBankAccount.getId(), dansBankAccount.getId(), "martha -> dan");
		executor.submit(creditMoneyThread);
		executor.submit(creditMoneyThread2);
		executor.submit(creditMoneyThread3);
		while(executor.getActiveCount()>0) {}
		executor.shutdown();
		tomsBankAccount = repository.findById(tomsBankAccount.getId()).get();
		dansBankAccount = repository.findById(dansBankAccount.getId()).get();
		marthasBankAccount = repository.findById(marthasBankAccount.getId()).get();
		BigDecimal sum = Stream.of(tomsBankAccount.getBalance(), dansBankAccount.getBalance(), marthasBankAccount.getBalance()).reduce((a,b)->a.add(b)).get();
		assertEquals(0, sum.compareTo(new BigDecimal(3000)));
	}
	
	public Runnable getCreditMoneyThread(int fromAccount, int toAccount, String threadName) {
		return () -> {
			Random rand = new Random();
			for(int x=0;x<100;x++) {
				BigDecimal amount = BigDecimal.ZERO;
				try {
					amount = new BigDecimal(rand.nextInt(1000));
					repository.createCredit(fromAccount, toAccount, amount);
				} catch (Exception e) {
					String from = repository.findById(fromAccount).get().getName();
					String to = repository.findById(toAccount).get().getName();
					System.out.println("Failed Transaction:" + " from:" + from + " to:" + to + " amount:" + amount);
				}
			}
		};
	}

	private void showDatabase() {
		System.out.println("*****");
		for (BankAccount bankAccount : repository.findAll()) {
			System.out.println(bankAccount.toString());
		}
		System.out.println("*****");
	}
	
}
