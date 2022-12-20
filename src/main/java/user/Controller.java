package user;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String test = req.getParameter("test");

        PrintWriter writer = resp.getWriter();
        try {
            writer.println("Hello World " + test);
        } catch (RuntimeException e) {
            System.out.println("Something went wrong.");
        } finally {
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String info = req.getServletPath();

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        PrintWriter writer = resp.getWriter();

        if (info == "/registration") {
            try {
                writer.println(info);
                writer.println("Name - " + name);
                writer.println("email - " + email);
                writer.println("password - " + password);

                User userNew = new User(name, email, password);

                UserRepository userRepository = new UserRepository();
                Connection connectionToDB = userRepository.getConnectionToDB();
                writer.println("????????????");
                userRepository.createNewUser(userNew);
                writer.println("????????????");

                writer.close();


            } catch (RuntimeException e) {
                System.out.println("Something went wrong.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Something went wrong witch DB.");
            } finally {
                writer.close();
            }


        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        PrintWriter writer = resp.getWriter();
        try {
            writer.println("Name - " + name);
            writer.println("email - " + email);
            writer.println("password - " + password);
        } catch (RuntimeException e) {
            System.out.println("Something went wrong.");
        } finally {
            writer.close();
        }

    }


    private User findUserInDB(String name, String email) {
        User user = null;

        return user;
    }
}
