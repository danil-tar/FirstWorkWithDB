package user;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String jdbcDriver = "org.postgresql.Driver";
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error load application. Cause: JDBC driver not found " + jdbcDriver);
        }
    }
}
