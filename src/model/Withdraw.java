package model;

import bank.Bank;
import simulation.SimulationUtility;

import javax.persistence.Entity;

@Entity
public class Withdraw extends AbstractOperation {
	private int amount;
	
	public Withdraw() {
		
	}

	public Withdraw(int serviceTime) {
		super(serviceTime);
	}

	@Override
	public void setBankAccount(int bankAccount, Bank bank){
		super.bankAccount = bankAccount;
		amount = SimulationUtility.getRandomAmount();
	}

	@Override
	public boolean isUrgent() {
		return true;
	}

	@Override
	public void processOperation(Bank bank) {
		if (bank.getBankAccount(super.bankAccount).getBalance() - amount >= 0) {
			bank.getBankAccount(super.bankAccount).setBalance(bank.getBankAccount(super.bankAccount).getBalance() - amount);

		}
	}

	@Override
	public String toString() {
		return "Operation : Withdraw";
	}

}
