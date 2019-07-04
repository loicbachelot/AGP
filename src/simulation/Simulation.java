package simulation;

import bank.Bank;
import bank.Cashier;
import bank.Queue;
import chart.*;
import model.AbstractClient;
import model.AbstractOperation;
import org.jfree.ui.RefineryUtilities;
import persistence.StatisticPersistence;

import java.util.List;

/**
 * System simulation. Access point of all related information.
 */
public class Simulation {
    private Bank bank;
    private StatisticManager statisticManager;
    private SimulationEntry simulationEntry;

    public Simulation() {

    }

    public void buildBank() {
        int cashierCount = simulationEntry.getCashierCount();
        bank = new Bank(cashierCount);
    }

    public void simulate() {
        int simulationDuration = simulationEntry.getSimulationDuration();
        int clientArrivalInterval = simulationEntry.getClientArrivalInterval();
        for (int currentSystemTime = 0; currentSystemTime <= simulationDuration; currentSystemTime++) {

            statisticManager.simulationDurationRecord();
            SimulationUtility.printBankStat(currentSystemTime, bank);

            updateBank(currentSystemTime);

            boolean newClientArrival = newClientArrival(clientArrivalInterval, currentSystemTime);
            if (newClientArrival) {
                int serviceTime = generateRandomServiceTime();
                double priorityClientRate = simulationEntry.getPriorityClientRate();
                AbstractClient client = SimulationUtility.getRandomClient(priorityClientRate);
                client.setArrivalTime(currentSystemTime);
                int clientPatienceTime = simulationEntry.getClientPatienceTime();
                client.setPatienceTime(clientPatienceTime);
                client.setBankAccount(SimulationUtility.getRandomClientBankAccount(bank));
                AbstractOperation operation = client.getOperation();
                operation.setServiceTime(serviceTime);
                operation.setBankAccount(SimulationUtility.getRandomClientBankAccount(bank), bank);
                Cashier freeCashier = bank.getFreeCashier();
                if (freeCashier == null) {
                    SimulationUtility.printClientArrival(currentSystemTime, false);
                    Queue queue = bank.getQueue();
                    queue.addQueueLast(client);
                } else {
                    SimulationUtility.printClientArrival(currentSystemTime, true);
                    serveClient(currentSystemTime, freeCashier, client);
                }
            }
        }
        statisticManager.setBankAccounts(bank.getBankAccountHashMap().values());
    }

    private void updateBank(int currentSystemTime) {
        List<Cashier> cashiers = bank.getCashiers();
        Queue queue = bank.getQueue();
        int cashierOccupied = 0;
        statisticManager.registerQueueSize(currentSystemTime, queue.size());
        for (Cashier cashier : cashiers) {

            if (!cashier.isFree()) {
                statisticManager.cashierOccupationRecord();
                cashierOccupied++;
            }

            cashier.work();

            if (cashier.serviceFinished()) {
                // Leaving client
                AbstractClient leavingClient = cashier.getServingClient();
                leavingClient.getOperation().processOperation(bank);
                leavingClient.setDepartureTime(currentSystemTime);
                SimulationUtility.printClientDeparture(currentSystemTime);
                statisticManager.registerServedClient(leavingClient);

                cashier.setServingClient(null);

                // Serve a client in the queue
                if (!queue.isEmpty()) {
                    AbstractClient nextClient;

                    nextClient = queue.findPriorityClient();
                    if (nextClient == null) {
                        nextClient = queue.getQueueFirst();
                    } else {
                        queue.removePriorityClient(nextClient);
                    }
                    serveClient(currentSystemTime, cashier, nextClient);
                }
            }
        }

        // Leaving impatient clients
        queue.updateClientPatience();
        List<AbstractClient> impatientClients = queue.removeImpatientClients();
        for (AbstractClient client : impatientClients) {
            client.setDepartureTime(currentSystemTime);
            statisticManager.registerNonServedClient(client);
            SimulationUtility.printClientDepartureWithoutBeingServed(currentSystemTime);
        }
        statisticManager.registerCashierOccupation(currentSystemTime, cashierOccupied);
    }

    private void showGraphicalSimulationResult(int idEntry) {
        HistogramGraphicalResult histogramGraphicalResult = new HistogramGraphicalResult(idEntry);
        histogramGraphicalResult.pack();
        RefineryUtilities.centerFrameOnScreen(histogramGraphicalResult);
        histogramGraphicalResult.setVisible(true);


        PieGraphicalResult pieGraphicalResult = new PieGraphicalResult(idEntry);
        pieGraphicalResult.pack();
        RefineryUtilities.centerFrameOnScreen(pieGraphicalResult);
        pieGraphicalResult.setVisible(true);

        BalanceChart balanceChart = new BalanceChart(idEntry);
        balanceChart.pack();
        RefineryUtilities.centerFrameOnScreen(balanceChart);
        balanceChart.setVisible(true);

        QueueChart queueChart = new QueueChart(idEntry);
        queueChart.pack();
        RefineryUtilities.centerFrameOnScreen(queueChart);
        queueChart.setVisible(true);

        OccupationChart occupationChart = new OccupationChart(idEntry);
        occupationChart.pack();
        RefineryUtilities.centerFrameOnScreen(occupationChart);
        occupationChart.setVisible(true);


        NumberOperationByAccountChart numberOperationByAccountChart = new NumberOperationByAccountChart(idEntry);
        numberOperationByAccountChart.pack();
        RefineryUtilities.centerFrameOnScreen(numberOperationByAccountChart);
        numberOperationByAccountChart.setVisible(true);


    }

    private int persistSimulationResult() {
        StatisticPersistence persistenceProxy = (StatisticPersistence) SpringContainer.getBean("hibernatePersistence");
        return persistenceProxy.persist(simulationEntry, statisticManager);
    }

    private void serveClient(int currentSystemTime, Cashier cashier, AbstractClient client) {
        client.setServiceStartTime(currentSystemTime);
        AbstractOperation operation = client.getOperation();
        int serviceTime = operation.getServiceTime();
        cashier.serve(client, serviceTime);
        SimulationUtility.printServiceTimeTrace(currentSystemTime, serviceTime);
    }

    private int generateRandomServiceTime() {
        int minServiceTime = simulationEntry.getMinServiceTime();
        int maxServiceTime = simulationEntry.getMaxServiceTime();
        int randomServiceTime = SimulationUtility.getRandomServiceTime(minServiceTime, maxServiceTime);
        return randomServiceTime;
    }

    private boolean newClientArrival(int clientArrivalInterval, int simIter) {
        return simIter % clientArrivalInterval == 0;
    }

    public String simulationResults() {
        StringBuffer results = new StringBuffer();
        results.append("########## Simulation results : #####################\n");
        results.append("Simulation Duration : " + simulationEntry.getSimulationDuration() + "\n");
        results.append("Served client count : " + statisticManager.servedClientCount() + "\n");
        results.append("Average client waiting time : " + statisticManager.calculateAverageClientWaitingTime() + " \n");
        results.append("Average client service time : " + statisticManager.calculateAverageClientServiceTime() + " \n");
        results.append("Cashier occupation rate : "
                + statisticManager.calculateAverageCashierOccupationRate(simulationEntry.getCashierCount()) + " % \n");
        results.append("Non-Served client count : " + statisticManager.nonServedClientCount() + "\n");
        results.append("Client satisfaction rate : " + statisticManager.calculateClientSatisfactionRate() + " %");
        return results.toString();
    }

    public void graphicalResults() {
        int idEntry = persistSimulationResult();
        showGraphicalSimulationResult(idEntry);
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public StatisticManager getStatisticManager() {
        return statisticManager;
    }

    public void setStatisticManager(StatisticManager statisticManager) {
        this.statisticManager = statisticManager;
    }

    public SimulationEntry getSimulationEntry() {
        return simulationEntry;
    }

    public void setSimulationEntry(SimulationEntry simulationEntry) {
        this.simulationEntry = simulationEntry;
    }

}
