package persistence;

import simulation.SimulationEntry;
import simulation.StatisticManager;

import java.util.HashMap;


/**
 * General interface for persistence APIs.
 */
public interface StatisticPersistence {

    int persist(SimulationEntry systemEntry, StatisticManager statisticManager);

    int servedClientCount(int simulationEntryId);

    int nonServedClientCount(int simulationEntryId);

    int transferOperationCount(int simulationEntryId);

    int withdrawOperationCount(int simulationEntryId);

    int consultationOperationCount(int simulationEntryId);

    HashMap<Integer, Double> balanceCount(int simulationEntryId);

    HashMap<Integer, Integer> numberOperationByAccount(int simulationEntryId);

    HashMap<Integer, Integer> queueSize(int simulationEntryId);

    HashMap<Integer, Integer> occupiedCashiers(int simulationEntryId);
}
