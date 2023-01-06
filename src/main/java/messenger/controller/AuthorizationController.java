package messenger.controller;

import messenger.dto.User;
import messenger.repository.UserRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class AuthorizationController extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String requestServletPath = request.getServletPath();

        if (requestServletPath == "/login") {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            UserRepository userRepository = new UserRepository();

            String userName = null;
            String userPassword = null;

            PrintWriter respWriter = null;
            try {
                User user = userRepository.getUser(email);
                userName = user.getName();
                userPassword = user.getPassword();

            } catch (NullPointerException | SQLException e) {
                try {
                    respWriter = response.getWriter();
                    respWriter.println("User is not fund!!!");
                } catch (IOException ex) {
                    System.out.println("Something went wrong with funding user.");
                    ex.printStackTrace();
                }
            }

            if (userName != null && userPassword.equals(password)) {
                PrintWriter writer = null;
                try {
                    writer = response.getWriter();
                    writer.println("Hello, " + userName + " you have successfully logged in!");
                } catch (RuntimeException | IOException e) {
                    System.out.println("Something went wrong with hello user.");
                    e.printStackTrace();
                } finally {
                    writer.close();
                }
            } else {
                try {
                    PrintWriter responseWriter = response.getWriter();
                    responseWriter.println("Wrong login or password");
                } catch (IOException e) {
                    System.out.println("Problem to response");
                    e.printStackTrace();
                }
            }
        }

    }
}