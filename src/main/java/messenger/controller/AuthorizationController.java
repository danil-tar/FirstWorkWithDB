package messenger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import messenger.dto.User;
import messenger.repository.UserRepository;
import messenger.service.AuthorizationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;

public class AuthorizationController extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        String nameRequest = request.getParameter("name");
        String emailRequest = request.getParameter("email");
        String passwordRequest = request.getParameter("password");
        AuthorizationService authorizationService = AuthorizationService.getInstance();
        AuthorizationResponse resultAuthorization = authorizationService.authorizationUser(nameRequest, emailRequest, passwordRequest);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PrintWriter responseWriter = response.getWriter();
            responseWriter.println(objectMapper.writeValueAsString(resultAuthorization));
        } catch (IOException e) {
            System.out.println("Problem witch response");
            e.printStackTrace();
        }
    }

    public static class AuthorizationResponse {
        private String result;
        private String token;

        public AuthorizationResponse(String result, String token) {
            this.result = result;
            this.token = token;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}