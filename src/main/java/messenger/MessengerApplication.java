package messenger;

import messenger.anotation.RegisterServlet;
import messenger.controller.RegistrationUserController;
import messenger.servlet.listener.ApplicationListener;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

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
            Class[] classes = getClasses("messenger.controller");
            for (Class aClass : classes) {
                if(aClass.isAnnotationPresent(RegisterServlet.class)){
                    RegisterServlet annotation = (RegisterServlet) aClass.getAnnotation(RegisterServlet.class);
                    String url = annotation.url();
                    HttpServlet httpServlet = (HttpServlet) aClass.getConstructor().newInstance();
                    tomcat.addServlet(context.getPath(), aClass.getName(), httpServlet);
                    context.addServletMappingDecoded(url, aClass.getName());
                }
            }

        } catch (ClassNotFoundException | IOException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
