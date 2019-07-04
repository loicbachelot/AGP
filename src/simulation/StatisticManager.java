package simulation;

import model.AbstractClient;
import model.BankAccount;

import java.util.*;

/**
 * This class collects statistic information. It calculates the simulation
 * result.
 */
public class StatisticManager {
    private List<AbstractClient> servedClients = new ArrayList<>();
    private List<AbstractClient> nonServedClients = new ArrayList<>();
    private int simulationDuration = 0;
    private int occupiedCashier = 0;
    private Collection<BankAccount> bankAccounts = new ArrayList<>();
    private Map<Integer, Integer> cashierOccupation = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> queueSize = new HashMap<Integer, Integer>();

    public StatisticManager() {

    }

    public List<AbstractClient> getServedClients() {
        return servedClients;
    }

    public void setServedClients(List<AbstractClient> servedClients) {
        this.servedClients = servedClients;
    }

    public List<AbstractClient> getNonServedClients() {
        return nonServedClients;
    }

    public void setNonServedClients(List<AbstractClient> nonServedClients) {
        this.nonServedClients = nonServedClients;
    }

    public void registerServedClient(AbstractClient client) {
        servedClients.add(client);
    }

    public void registerNonServedClient(AbstractClient client) {
        nonServedClients.add(client);
    }

    public void simulationDurationRecord() {
        simulationDuration++;
    }

    public void cashierOccupationRecord() {
        occupiedCashier++;
    }

    public void registerCashierOccupation(int time, int number) {
        cashierOccupation.put(time, number);
    }

    public void registerQueueSize(int time, int size) {
        queueSize.put(time, size);
    }

    public double calculateAverageCashierOccupationRate(int cashierCount) {
        return (occupiedCashier * 100 / simulationDuration) / cashierCount;
    }

    public double calculateAverageClientWaitingTime() {
        int totalWaitingTime = 0;
        for (AbstractClient client : servedClients) {
            int serviceStartTime = client.getServiceStartTime();
            int arrivalTime = client.getArrivalTime();
            totalWaitingTime += serviceStartTime - arrivalTime;
        }
        int servedClientCount = servedClientCount();
        return totalWaitingTime / servedClientCount;
    }

    public double calculateAverageClientServiceTime() {
        int totalServiceTime = 0;
        for (AbstractClient client : servedClients) {
            int departureTime = client.getDepartureTime();
            int serviceStartTime = client.getServiceStartTime();
            totalServiceTime += departureTime - serviceStartTime;
        }
        return totalServiceTime / servedClientCount();
    }

    public int servedClientCount() {
        return servedClients.size();
    }

    public int nonServedClientCount() {
        return nonServedClients.size();
    }

    public double calculateClientSatisfactionRate() {
        return servedClientCount() * 100
                / (servedClientCount() + nonServedClientCount());
    }

    public Collection<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Collection<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public Map<Integer, Integer> getCashierOccupation() {
        return cashierOccupation;
    }

    public void setCashierOccupation(Map<Integer, Integer> cashierOccupation) {
        this.cashierOccupation = cashierOccupation;
    }

    public Map<Integer, Integer> getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Map<Integer, Integer> queueSize) {
        this.queueSize = queueSize;
    }
}
