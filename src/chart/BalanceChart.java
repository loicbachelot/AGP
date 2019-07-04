package chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import persistence.StatisticPersistence;
import simulation.SpringContainer;

import javax.swing.*;
import java.util.HashMap;

public class BalanceChart extends ApplicationFrame {
    private static final String TITLE = "Client's Bank Accounts Balance";
    private static final long serialVersionUID = 1L;
    private int idEntry;

    public BalanceChart(int idEntry) {
        super(TITLE);
        this.idEntry = idEntry;
        setContentPane(createDemoPanel());
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        StatisticPersistence persistence = (StatisticPersistence) SpringContainer.getBean("hibernatePersistence");
        HashMap<Integer, Double> accounts = persistence.balanceCount(idEntry);

        for (Integer accountNumber : accounts.keySet()) {
            dataset.addValue(accounts.get(accountNumber), accountNumber, "Bank Accounts");

        }
        return dataset;
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset) {
        return ChartFactory.createBarChart(TITLE, "category", "number of operation", dataset);
    }

    public JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }
}

