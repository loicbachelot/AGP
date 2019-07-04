package simulation;

import model.AbstractClient;
import model.AbstractOperation;
import org.aspectj.lang.JoinPoint;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ServiceReporter {
    private static final String REPORT_FILE = "src/simulation/ProblemReport.txt";

    public void nonServedClientEntry(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        AbstractClient client = (AbstractClient) args[0];
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FILE, true));
            writer.write("Dear boss, today, there is another client who has left without being served !");
            writer.newLine();
            writer.write("Here is some information about the upset client : ");
            writer.newLine();
            writer.write("Arrival time : " + client.getArrivalTime());
            writer.newLine();
            writer.write("Departure time : " + client.getDepartureTime());
            writer.newLine();
            writer.write("-----------------------------------------------------------------------------");
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public void processed(JoinPoint joinPoint) {


        Object[] args = joinPoint.getArgs();
        AbstractOperation operation = ((AbstractClient) args[0]).getOperation();

        String operationSummary = new String();
        operationSummary += "Dear boss, today, there is another client who made a transfer \n";
        operationSummary += operation.toString();


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FILE, true));
            writer.write(operationSummary);
            writer.write("\n------------------------------------------------------------\n");

            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
