package chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import persistence.StatisticPersistence;
import simulation.SpringContainer;

import javax.swing.*;

public class HistogramGraphicalResult extends ApplicationFrame {
    private static final String TITLE = "Clients operations distribution";
    private static final long serialVersionUID = 1L;
    private int idEntry;

    public HistogramGraphicalResult(int idEntry) {
        super(TITLE);
        this.idEntry = idEntry;
        setContentPane(createDemoPanel());
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        StatisticPersistence persistence = (StatisticPersistence) SpringContainer.getBean("hibernatePersistence");
        int nbTransfer = persistence.transferOperationCount(idEntry);
        int nbConsultation = persistence.consultationOperationCount(idEntry);
        int nbWithdraw = persistence.withdrawOperationCount(idEntry);
        dataset.addValue(nbTransfer, "Transfers", "number of operation");
        dataset.addValue(nbWithdraw, "Withdraw", "number of operation");
        dataset.addValue(nbTransfer, "Consultations", "number of operation");

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

