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

public class NumberOperationByAccountChart extends ApplicationFrame {
    private static final String TITLE = "Number of operations by account";
    private static final long serialVersionUID = 1L;
    private int idEntry;

    public NumberOperationByAccountChart(int idEntry) {
        super(TITLE);
        this.idEntry = idEntry;
        setContentPane(createDemoPanel());
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        StatisticPersistence persistence = (StatisticPersistence) SpringContainer.getBean("hibernatePersistence");
        HashMap<Integer, Integer> map  = persistence.numberOperationByAccount(idEntry);

        for (Integer accountNumber : map.keySet()) {
            dataset.addValue(map.get(accountNumber), "Account number",accountNumber );
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
