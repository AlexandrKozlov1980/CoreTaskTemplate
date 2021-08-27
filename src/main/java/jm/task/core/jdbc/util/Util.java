package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.spi.Mapping;
//import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String BD_URL = "jdbc:mysql://localhost:3306/idea";
    private static final String BD_USER = "root";
    private static final String BD_PASSWORD = "qw009719er";
    private static final String BD_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static SessionFactory sessionFactory;



    public static Connection setConnection() {
        Connection con = null;
        try {
            Class.forName(BD_DRIVER);
            con = DriverManager.getConnection(BD_URL, BD_USER, BD_PASSWORD);
            //System.out.println("Соединение установленно");
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Ошибка соединения");
        }
        return con;
    }

    public static SessionFactory getSessionFactory(){

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();

                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/idea");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "qw009719er");
                settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
