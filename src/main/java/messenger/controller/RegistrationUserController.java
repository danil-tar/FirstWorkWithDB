package messenger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import messenger.anotation.RegisterServlet;
import messenger.dto.User;
import messenger.repository.ReferralRepository;
import messenger.repository.UserRepository;
import messenger.service.CreateUserService;
import messenger.service.ReferralService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

@RegisterServlet(url = "/registration")
public class RegistrationUserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedReader reader = req.getReader();
        String collect = reader.lines().collect(Collectors.joining(" "));

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(collect, User.class);

        String partnerIdFromReq = req.getParameter("partnerId");
        PrintWriter writer = resp.getWriter();

        CreateUserService createUserService = CreateUserService.getInstance();
        String resultRegistration = createUserService.registrationNewUser(user, partnerIdFromReq);
        RegistrationResponse registrationResponse = new RegistrationResponse(resultRegistration);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(registrationResponse);
        writer.println(jsonResponse);

        writer.close();

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
