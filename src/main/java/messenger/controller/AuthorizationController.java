package messenger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import messenger.anotation.RegisterServlet;
import messenger.service.AuthorizationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@RegisterServlet(url = "/login")
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
            responseWriter.close();
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
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