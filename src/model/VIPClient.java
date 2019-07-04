package model;

import javax.persistence.Entity;

@Entity
public class VIPClient extends AbstractClient {
	
	public VIPClient() {
		
	}

	public VIPClient(int arrivalTime, AbstractOperation operation, int patienceTime) {
		super(arrivalTime, operation, patienceTime);
	}

	@Override
	public boolean isPriority() {
		return true;
	}

	@Override
	public String toString() {
		return "Priority " + super.toString();
	}

}
