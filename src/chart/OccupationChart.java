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

public class OccupationChart extends ApplicationFrame {
    private static final String TITLE = "Cashiers occupation over time";
    private static final long serialVersionUID = 1L;
    private int idEntry;

    public OccupationChart(int idEntry) {
        super(TITLE);
        this.idEntry = idEntry;
        setContentPane(createDemoPanel());
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        StatisticPersistence persistence = (StatisticPersistence) SpringContainer.getBean("hibernatePersistence");
        HashMap<Integer, Integer> occupiedCashiers = persistence.occupiedCashiers(idEntry);

        for (Integer time : occupiedCashiers.keySet()) {
            dataset.addValue(occupiedCashiers.get(time), "time", time.toString());
        }
        return dataset;
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset) {
        return ChartFactory.createLineChart(TITLE, "Time", "occupation", dataset);
    }

    public JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }


}

