package persistence;

import bank.Bank;
import model.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import simulation.SimulationEntry;

public class DBConnection {
    private static SessionFactory sessionFactory;
    private static AnnotationConfiguration config;

    public static AnnotationConfiguration getConfig() {
        if (config == null) {
            config = new AnnotationConfiguration();
            config.addAnnotatedClass(SimulationEntry.class);
            config.addAnnotatedClass(AbstractClient.class);
            config.addAnnotatedClass(Client.class);
            config.addAnnotatedClass(VIPClient.class);
            config.addAnnotatedClass(AbstractOperation.class);
            config.addAnnotatedClass(Consultation.class);
            config.addAnnotatedClass(Transfer.class);
            config.addAnnotatedClass(Withdraw.class);
            config.addAnnotatedClass(Bank.class);
            config.addAnnotatedClass(BankAccount.class);

            String packageName = DBConnection.class.getPackage().getName();
            config.configure(packageName + "/connection.cfg.xml");
        }
        return config;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                AnnotationConfiguration config = getConfig();
                sessionFactory = config.buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }
}
