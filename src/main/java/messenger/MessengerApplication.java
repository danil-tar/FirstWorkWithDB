package messenger;

import messenger.annotation.RegisterServlet;
import messenger.menegment.InstanceFactory;
import messenger.menegment.ReflectionUtil;
import messenger.servlet.listener.ApplicationListener;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MessengerApplication {

    public static void main(String[] args) {
       startTomcat();
    }

    public static void startTomcat()  {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        Context context = tomcat.addContext("/messenger", new File(".").getAbsolutePath());

        context.addApplicationListener(ApplicationListener.class.getName());
        context.addParameter("jdbcDriver", "org.postgresql.Driver");
        InstanceFactory.registerAllInstances();
        registerServlets(tomcat, context);

        try {
            tomcat.  start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
        tomcat.getServer().await();

    }

    private static void registerServlets(Tomcat tomcat, Context context) {

        try {
            Class<?>[] classes = ReflectionUtil.getClasses("messenger.controller");
            for (Class<?> aClass : classes) {
                if(aClass.isAnnotationPresent(RegisterServlet.class)){
                    RegisterServlet annotation = (RegisterServlet) aClass.getAnnotation(RegisterServlet.class);
                    String url = annotation.url();
                    Constructor<?> constructor = aClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    HttpServlet httpServlet = (HttpServlet) constructor.newInstance();
                    tomcat.addServlet(context.getPath(), aClass.getName(), httpServlet);
                    context.addServletMappingDecoded(url, aClass.getName());
                }
            }

        } catch (ClassNotFoundException | IOException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
