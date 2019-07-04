package model;

import bank.Bank;

import javax.persistence.Entity;

@Entity
public class Consultation extends AbstractOperation {
	
	public Consultation() {
		
	}

	public Consultation(int serviceTime) {
		super(serviceTime);
	}

	@Override
	public boolean isUrgent() {
		return false;
	}

	@Override
	public void processOperation(Bank bank) {
		System.out.println("Balance on the account: " + bank.getBankAccount(super.bankAccount).getBalance());
	}

	@Override
	public String toString() {
		return "Operation : Consultation";
	}
}
