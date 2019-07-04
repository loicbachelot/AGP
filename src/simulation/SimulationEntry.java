package simulation;

import bank.Bank;
import model.AbstractClient;
import model.BankAccount;

import javax.persistence.*;
import java.util.*;

/**
 * This class regroups simulation entry parameters.
 */
@Entity
public class SimulationEntry {
    @Id
    @GeneratedValue
    private int id;
    private int simulationDuration;
    private int cashierCount;
    private int minServiceTime;
    private int maxServiceTime;
    private double maxAmountBalance;
    private int clientArrivalInterval;
    private double priorityClientRate;
    private int clientPatienceTime;
    private int maxBankAccountNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AbstractClient.class)
    private List<AbstractClient> allClients = new ArrayList<AbstractClient>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = BankAccount.class)
    private Collection<BankAccount> bankAccounts = new ArrayList<BankAccount>();

    @ElementCollection
    private Map<Integer, Integer> cashierOccupation = new HashMap<Integer, Integer>();

    @ElementCollection
    private Map<Integer, Integer> queueSize = new HashMap<Integer, Integer>();

    public SimulationEntry() {

    }

    public SimulationEntry(int simulationDuration, int cashierCount, int minServiceTime, int maxServiceTime,
                           int clientArrivalInterval, double priorityClientRate, int clientPatienceTime, double maxAmountBalance, int maxBankAccountNumber) {
        this.simulationDuration = simulationDuration;
        this.cashierCount = cashierCount;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.clientArrivalInterval = clientArrivalInterval;
        this.priorityClientRate = priorityClientRate;
        this.clientPatienceTime = clientPatienceTime;
        this.maxAmountBalance = maxAmountBalance;
        this.maxBankAccountNumber = maxBankAccountNumber;
    }

    public void setSimulationDuration(int simulationDuration) {
        this.simulationDuration = simulationDuration;
    }

    public void setCashierCount(int cashierCount) {
        this.cashierCount = cashierCount;
    }

    public void setMinServiceTime(int minServiceTime) {
        this.minServiceTime = minServiceTime;
    }

    public void setMaxServiceTime(int maxServiceTime) {
        this.maxServiceTime = maxServiceTime;
    }

    public void setClientArrivalInterval(int clientArrivalInterval) {
        this.clientArrivalInterval = clientArrivalInterval;
    }

    public void setPriorityClientRate(double priorityClientRate) {
        this.priorityClientRate = priorityClientRate;
    }

    public void setClientPatienceTime(int clientPatienceTime) {
        this.clientPatienceTime = clientPatienceTime;
    }

    public int getSimulationDuration() {
        return simulationDuration;
    }

    public int getCashierCount() {
        return cashierCount;
    }

    public int getMinServiceTime() {
        return minServiceTime;
    }

    public int getMaxServiceTime() {
        return maxServiceTime;
    }

    public int getClientArrivalInterval() {
        return clientArrivalInterval;
    }

    public double getPriorityClientRate() {
        return priorityClientRate;
    }

    public int getClientPatienceTime() {
        return clientPatienceTime;
    }

    public List<AbstractClient> getAllClients() {
        return allClients;
    }

    public void setAllClients(List<AbstractClient> allClients) {
        this.allClients = allClients;
    }

    public double getMaxAmountBalance() {
        return maxAmountBalance;
    }

    public void setMaxAmountBalance(double maxAmountBalance) {
        this.maxAmountBalance = maxAmountBalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getMaxBankAccountNumber() {
        return maxBankAccountNumber;
    }

    public void setMaxBankAccountNumber(int maxBankAccountNumber) {
        this.maxBankAccountNumber = maxBankAccountNumber;
    }
}
