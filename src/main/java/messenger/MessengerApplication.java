package messenger;

import messenger.controller.RegistrationUserController;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class MessengerApplication {

    public static void main(String[] args) {
       startTomcat();
    }

    public static void startTomcat()  {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        Context context = tomcat.addContext("/messenger", new File(".").getAbsolutePath());

        context.addApplicationListener("messenger.servlet.listener.ApplicationListener");
        context.addParameter("jdbcDriver", "org.postgresql.Driver");


        Wrapper registrationServlet = tomcat
                .addServlet(context.getPath(), "registrationServlet", new RegistrationUserController());

        context.addServletMappingDecoded("/registration", "registrationServlet");

        // configure the server
// configure web applications

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
        tomcat.getServer().await();
    }
}
