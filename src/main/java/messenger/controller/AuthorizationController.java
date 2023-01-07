package messenger.controller;

import messenger.dto.User;
import messenger.repository.UserRepository;
import messenger.service.AuthorizationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class AuthorizationController extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        String nameRequest = request.getParameter("name");
        String emailRequest = request.getParameter("email");
        String passwordRequest = request.getParameter("password");

        AuthorizationService authorizationService = new AuthorizationService();
        String resultAuthorization = authorizationService.authorizationUser(nameRequest, emailRequest, passwordRequest);

        try {
            PrintWriter responseWriter = response.getWriter();
            responseWriter.println(resultAuthorization);
        } catch (IOException e) {
            System.out.println("Problem witch response");
            e.printStackTrace();
        }


    }
}