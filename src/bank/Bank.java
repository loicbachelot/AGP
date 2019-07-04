package bank;

import model.AbstractClient;
import model.BankAccount;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A bank is composed of cashiers and a queue.
 */

@Entity
public class Bank {
	@Id
	@GeneratedValue
	private int id;
	@Transient
	private List<Cashier> cashiers = new ArrayList<Cashier>();
	@Transient
	private Queue queue = new Queue();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = BankAccount.class)
	private Map<Integer, BankAccount> bankAccountHashMap = new HashMap<>();

	public Bank(int cashierCount) {
		createCashiers(cashierCount);
	}

	public List<Cashier> getCashiers() {
		return cashiers;
	}

	public Queue getQueue() {
		return queue;
	}

	private void createCashiers(int cashierCount) {
		for (int cashierId = 1; cashierId <= cashierCount; cashierId++) {
			Cashier cashier = new Cashier();
			cashiers.add(cashier);
		}
	}

	public Cashier getFreeCashier() {
		for (Cashier cashier : cashiers) {
			if (cashier.isFree()) {
				return cashier;
			}
		}
		return null;
	}

	public String toString() {
		StringBuffer results = new StringBuffer();
		results.append(queue.toString() + "\n");
		results.append("Cashiers : ");
		for (Cashier cashier : cashiers) {
			results.append(cashier.toString() + " ");
		}
		return results.toString();
	}

	public BankAccount getBankAccount(int bankAccountNumber) {
		BankAccount bankAccount = bankAccountHashMap.get(bankAccountNumber);
        if (bankAccount == null){
	        bankAccount = new BankAccount(bankAccountNumber);
	        bankAccountHashMap.put(bankAccountNumber, bankAccount);
	        return bankAccount;
        }else {
        	return bankAccount;
		}
    }

    public Map<Integer, BankAccount> getBankAccountHashMap() {
        return bankAccountHashMap;
    }

    public void addBankAccount(BankAccount bankAccount){
	    bankAccountHashMap.put(bankAccount.getAccountNumber(), bankAccount);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
