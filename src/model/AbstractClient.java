package model;

import javax.persistence.*;

/**
 * We need to know when a client arrives, when the service starts and finishes.
 * When creating the client, only his arrival time can be defined.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractClient {
	@Id
	@GeneratedValue
	private int id;
	private int arrivalTime;
	private int serviceStartTime;
	private int departureTime;
	private boolean served;

	@OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AbstractOperation.class)
	private AbstractOperation operation;
	private int patienceTime;
	private int bankAccount;

	public AbstractClient() {
		
	}
	
	public AbstractClient(int arrivalTime, AbstractOperation operation, int patienceTime) {
		this.arrivalTime = arrivalTime;
		this.operation = operation;
		this.patienceTime = patienceTime;
	}
	
	public abstract boolean isPriority();

	public boolean isServed() {
		return served;
	}

	public void setServed(boolean served) {
		this.served = served;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(int departureTime) {
		this.departureTime = departureTime;
	}

	public int getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(int serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public AbstractOperation getOperation() {
		return operation;
	}

	public void reducePatience() {
		if (patienceTime > 0) {
			patienceTime--;
		}
	}

	public boolean isPatient() {
		return patienceTime > 0 || (operation.isUrgent());
	}

	public int getPatienceTime() {
		return patienceTime;
	}

	public void setPatienceTime(int patienceTime) {
		this.patienceTime = patienceTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setOperation(AbstractOperation operation) {
		this.operation = operation;
	}

	public String toString() {
		return "Client[arrival : " + arrivalTime + ", patience time : " + patienceTime + ", " + operation.toString() + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setBankAccount(int bankAccount) {
		this.bankAccount = bankAccount;
	}

	public int getBankAccount() {
		return bankAccount;
	}
}
