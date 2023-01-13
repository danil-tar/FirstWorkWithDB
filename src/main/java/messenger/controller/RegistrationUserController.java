package messenger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import messenger.dto.User;
import messenger.service.CreateUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class RegistrationUserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedReader reader = req.getReader();
        String collect = reader.lines().collect(Collectors.joining(" "));

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(collect, User.class);

        PrintWriter writer = resp.getWriter();

        try {
            CreateUserService createUserService = new CreateUserService();
            String resultRegistration = createUserService.registrationNewUser(user);

            RegistrationResponse registrationResponse = new RegistrationResponse(resultRegistration);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(registrationResponse);
            writer.println(jsonResponse);

            writer.close();

        } catch (RuntimeException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        } finally {
            writer.close();
        }

    }

    static class RegistrationResponse {
        private String result;

        public RegistrationResponse(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
