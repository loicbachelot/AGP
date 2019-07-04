package model;

import org.apache.commons.lang.math.RandomUtils;
import simulation.SimulationEntry;
import simulation.SpringContainer;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;


/**
 * Created by mady on 27/01/18.
 *
 * @author mady
 */
@Entity
public class BankAccount {
    @Id
    private int accountNumber;
    private double balance;

    public BankAccount() {
    }

    public BankAccount(int accountNumber) {
        SimulationEntry entry = (SimulationEntry) SpringContainer.getBean("entry");
        this.accountNumber = accountNumber;
        this.balance = RandomUtils.nextDouble() * entry.getMaxAmountBalance();
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAccountNumber(), getBalance());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return getAccountNumber() == that.getAccountNumber();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
