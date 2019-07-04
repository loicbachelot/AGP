package simulation;

import persistence.DataInit;

public class TestSimulation {

	public static void main(String[] args) {
		DataInit.createTables();
		Simulation simulation = (Simulation) SpringContainer.getBean("simulation");
		simulation.buildBank();
		simulation.simulate();
		System.out.println(simulation.simulationResults());
		//This part is disabled for that you can concentrate on the Spring part.
		simulation.graphicalResults();
	}
}
