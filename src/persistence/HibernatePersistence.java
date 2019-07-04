package persistence;

import model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import simulation.SimulationEntry;
import simulation.SpringContainer;
import simulation.StatisticManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class HibernatePersistence implements StatisticPersistence {

    @Override
    public int persist(SimulationEntry systemEntry, StatisticManager statisticManager) {

        Session session = DBConnection.getSession();
        Transaction persistTransaction1 = session.beginTransaction();

        List<AbstractClient> servedClients = statisticManager.getServedClients();
        List<AbstractClient> nonServedClients = statisticManager.getNonServedClients();
        servedClients.addAll(nonServedClients);
        systemEntry.setAllClients(servedClients);
        systemEntry.setBankAccounts(statisticManager.getBankAccounts());
        systemEntry.setCashierOccupation(statisticManager.getCashierOccupation());
        systemEntry.setQueueSize(statisticManager.getQueueSize());

        Serializable id = session.save(systemEntry);
        persistTransaction1.commit();

        session.close();
        return (Integer) id;
    }

    @Override
    public int servedClientCount(int simulationEntryId) {
        Session session = DBConnection.getSession();
        Transaction readTransaction = session.beginTransaction();
        Query readQuery = session.createQuery("from SimulationEntry se where se.id = :id");
        readQuery.setInteger("id", simulationEntryId);
        List result = readQuery.list();
        SimulationEntry entry = (SimulationEntry) result.get(0);
        int count = 0;
        for (AbstractClient client : entry.getAllClients()) {
            if (client.isServed()) {
                count++;
            }
        }
        readTransaction.commit();
        session.close();

        return count;
    }

    @Override
    public int nonServedClientCount(int simulationEntryId) {
        Session session = DBConnection.getSession();
        Transaction readTransaction = session.beginTransaction();
        Query readQuery = session.createQuery("from SimulationEntry se where se.id = :id");
        readQuery.setInteger("id", simulationEntryId);
        List result = readQuery.list();
        SimulationEntry entry = (SimulationEntry) result.get(0);
        int count = 0;
        for (AbstractClient client : entry.getAllClients()) {
            if (!client.isServed()) {
                count++;
            }
        }
        readTransaction.commit();
        session.close();

        return count;
    }

    @Override
    public int transferOperationCount(int simulationEntryId) {
        Session session = DBConnection.getSession();
        Transaction readTransaction = session.beginTransaction();
        Query readQuery = session.createQuery("from Transfer");
        List result = readQuery.list();
        int count = result.size();
        readTransaction.commit();
        session.close();
        return count;
    }

    @Override
    public int withdrawOperationCount(int simulationEntryId) {
        Session session = DBConnection.getSession();
        Transaction readTransaction = session.beginTransaction();
        Query readQuery = session.createQuery("from Withdraw");
        List result = readQuery.list();
        int count = result.size();
        readTransaction.commit();
        session.close();
        return count;
    }

    @Override
    public int consultationOperationCount(int simulationEntryId) {
        Session session = DBConnection.getSession();
        Transaction readTransaction = session.beginTransaction();
        Query readQuery = session.createQuery("from Consultation");
        List result = readQuery.list();
        int count = result.size();
        readTransaction.commit();
        session.close();
        return count;
    }

    @Override
    public HashMap<Integer, Integer> numberOperationByAccount(int simulationEntryId) {
        Session session = DBConnection.getSession();
        Transaction readTransaction = session.beginTransaction();
        Query readQuery = session.createQuery("from AbstractOperation");
        List result = readQuery.list();
        HashMap<Integer, Integer> resultMap = new HashMap<>();
        SimulationEntry entry = (SimulationEntry) SpringContainer.getBean("entry");
        for (int i = 0; i < entry.getMaxBankAccountNumber(); i++) {
            resultMap.put(i + 1, 0);
        }

        for (Object r : result) {
            AbstractOperation operation = (AbstractOperation) r;
            resultMap.put(((AbstractOperation) r).getBankAccount(), resultMap.get(((AbstractOperation) r).getBankAccount()) + 1);
        }
        readTransaction.commit();
        session.close();
        return resultMap;
    }


    public HashMap<Integer, Double> balanceCount(int simulationEntryId) {
        Session session = DBConnection.getSession();
        Transaction readTransaction = session.beginTransaction();
        Query readQuery = session.createQuery("from SimulationEntry se where se.id = :id");
        readQuery.setInteger("id", simulationEntryId);
        List result = readQuery.list();
        SimulationEntry entry = (SimulationEntry) result.get(0);
        HashMap<Integer, Double> accountHashMap = new HashMap<>();

        for (BankAccount account :
                entry.getBankAccounts()) {
            accountHashMap.put(account.getAccountNumber(), account.getBalance());
        }
        readTransaction.commit();
        session.close();

        return accountHashMap;
    }

    public HashMap<Integer, Integer> queueSize(int simulationEntryId) {
        Session session = DBConnection.getSession();
        Transaction readTransaction = session.beginTransaction();
        Query readQuery = session.createQuery("from SimulationEntry se where se.id = :id");
        readQuery.setInteger("id", simulationEntryId);
        List result = readQuery.list();
        SimulationEntry entry = (SimulationEntry) result.get(0);
        HashMap<Integer, Integer> queueHashMap = new HashMap<>(entry.getQueueSize());
        readTransaction.commit();
        session.close();

        return queueHashMap;
    }

    public HashMap<Integer, Integer> occupiedCashiers(int simulationEntryId) {
        Session session = DBConnection.getSession();
        Transaction readTransaction = session.beginTransaction();
        Query readQuery = session.createQuery("from SimulationEntry se where se.id = :id");
        readQuery.setInteger("id", simulationEntryId);
        List result = readQuery.list();
        SimulationEntry entry = (SimulationEntry) result.get(0);
        HashMap<Integer, Integer> queueHashMap = new HashMap<>(entry.getCashierOccupation());
        readTransaction.commit();
        session.close();

        return queueHashMap;
    }


}