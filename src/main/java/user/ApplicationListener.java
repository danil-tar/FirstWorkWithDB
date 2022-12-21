package user;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String jdbcDriver = sce.getServletContext()
                .getInitParameter("jdbcDriver");

        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            System.out.println("Upps" + e.getMessage());
            throw new RuntimeException("Error load application. Cause: JDBC driver not found " + jdbcDriver);
        }
    }
}
