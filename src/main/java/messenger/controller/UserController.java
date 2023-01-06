package messenger.controller;

import messenger.dto.User;
import messenger.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class UserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String emailFromRequest = req.getParameter("email");
        UserRepository userRepository = new UserRepository();

        String userName = null;
        try {
            User user = userRepository.getUser(emailFromRequest);
            userName = user.getName();
        } catch (NullPointerException | SQLException e) {
            PrintWriter respWriter = resp.getWriter();
            respWriter.println("User is not fund!!!");
        }
        if (userName != null) {
            PrintWriter writer = resp.getWriter();
            try {
                writer.println("Hello, " + userName);
            } catch (RuntimeException e) {
                System.out.println("Something went wrong.");
            } finally {
                writer.close();
            }
        }
    }






}
