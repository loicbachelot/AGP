package simulation;

import model.AbstractClient;
import bank.Bank;

/**
 * Provides utility static methods for simulation.
 */
public class SimulationUtility {
	private static int getRandomNumber(int min, int max) {
		return (int) (Math.random() * (max + 1 - min)) + min;
	}

	public static int getRandomServiceTime(int min, int max) {
		return getRandomNumber(min, max);
	}

	public static void printServiceTimeTrace(int currentSystemTime, int serviceTime) {
		System.out.println("Start service for " + serviceTime + " time units.");
	}

	public static void printClientArrival(int currentSystemTime, boolean served) {
		if (served) {
			System.out.println("A new client arrives and is served immediately.");
		} else {
			System.out.println("A new client arrives and joins the queue.");
		}
	}

	public static void printClientDeparture(int currentSystemTime) {
		System.out.println("A client leaves after service.");
	}

	public static void printClientDepartureWithoutBeingServed(int currentSystemTime) {
		System.out.println("A client leaves without being served.");
	}

	public static void printBankStat(int currentSystemTime, Bank bank) {
		System.out.println("########### At time : " + currentSystemTime + " ##########");
		System.out.println(bank.toString());
	}

	public static boolean isPriorityClient(double priorityClientRate) {
		double random = Math.random();
		return random < priorityClientRate;
	}

	public static AbstractClient getRandomClient(double priorityClientRate) {
		if (isPriorityClient(priorityClientRate)) {
			int operationNumber = getRandomNumber(1, 3);
			switch (operationNumber) {
			case 1:
				return (AbstractClient) SpringContainer.getBean("vipConsultation");
			case 2:
				return (AbstractClient) SpringContainer.getBean("vipTransfer");
			case 3:
				return (AbstractClient) SpringContainer.getBean("vipWithdraw");
			}
		} else {
			int operationNumber = getRandomNumber(1, 3);
			switch (operationNumber) {
			case 1:
				return (AbstractClient) SpringContainer.getBean("normalConsultation");
			case 2:
				return (AbstractClient) SpringContainer.getBean("normalTransfer");
			case 3:
				return (AbstractClient) SpringContainer.getBean("normalWithdraw");
			}
		}
		return null;
	}

	public static int getRandomClientBankAccount(Bank bank) {
		SimulationEntry entry = (SimulationEntry) SpringContainer.getBean("entry");
		int bankAccountNumber = getRandomNumber(1, entry.getMaxBankAccountNumber());
		return bank.getBankAccount(bankAccountNumber).getAccountNumber();
	}

	public static int getRandomAmount() {
		return getRandomNumber(1,300);
	}
}
