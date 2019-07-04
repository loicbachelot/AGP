package model;

import bank.Bank;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractOperation {
	@Id
	@GeneratedValue
	private int id;
	private int serviceTime;

	protected int bankAccount;
	
	public AbstractOperation() {
		
	}

	public int getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(int bankAccount, Bank bank) {
		this.bankAccount = bankAccount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public AbstractOperation(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public abstract boolean isUrgent();

	public abstract void processOperation(Bank bank);


}
