package messenger.controller;

import messenger.service.CreateUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationUserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        PrintWriter writer = resp.getWriter();

        try {
            CreateUserService newUserRegistration = new CreateUserService();
            String resultRegistration = newUserRegistration.registrationNewUser(name, email, password);
            writer.println(resultRegistration + "witch " + email);

            writer.close();

        } catch (RuntimeException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        } finally {
            writer.close();
        }

    }

}
