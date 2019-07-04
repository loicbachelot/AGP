package model;

import bank.Bank;
import simulation.SimulationUtility;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Transfer extends AbstractOperation{
	private int destinationAccount;
	private int amount;

	public Transfer() {

	}
	
	public Transfer(int serviceTime) {
		super(serviceTime);
	}

	@Override
	public void setBankAccount(int bankAccount, Bank bank){
		super.bankAccount = bankAccount;
		destinationAccount = SimulationUtility.getRandomClientBankAccount(bank);
		amount = SimulationUtility.getRandomAmount();
	}

	public void setDestinationAccount(int account) {
		this.destinationAccount = account;
	}

	public void setAmount (int amount){
		this.amount = amount;
	}

	@Override
	public void processOperation(Bank bank) {
		if (bank.getBankAccount(super.bankAccount).getBalance() - amount >= 0){
			bank.getBankAccount(super.bankAccount).setBalance(bank.getBankAccount(super.bankAccount).getBalance() - amount);
			bank.getBankAccount(destinationAccount).setBalance(bank.getBankAccount(destinationAccount).getBalance() + amount);
		}
	}

	@Override
	public boolean isUrgent() {
		return true;
	}

	@Override
	public String toString() {
		return "Operation : Transfer";
	}

}
