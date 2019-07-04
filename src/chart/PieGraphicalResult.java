package chart;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

import persistence.StatisticPersistence;
import simulation.SpringContainer;

public class PieGraphicalResult extends ApplicationFrame {

	private static final String TITLE = "Served clients VS non served clients";
	private static final long serialVersionUID = 1L;
	private int idEntry;

	public PieGraphicalResult(int idEntry) {
		super(TITLE);
		this.idEntry = idEntry;
		setContentPane(createDemoPanel());
	}

	private PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		StatisticPersistence persistence = (StatisticPersistence) SpringContainer.getBean("hibernatePersistence");
		int servedClientCount = persistence.servedClientCount(idEntry);
		int nonServedClientCount = persistence.nonServedClientCount(idEntry);
		dataset.setValue("Served clients", servedClientCount);
		dataset.setValue("Non served clients", nonServedClientCount);
		return dataset;
	}

	private JFreeChart createChart(PieDataset dataset) {
		return ChartFactory.createPieChart(TITLE, dataset, true, true, false);
	}

	public JPanel createDemoPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
}