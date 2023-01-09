package messenger.controller;

import messenger.service.DeleteUserService;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;

public class DeleteUserController extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        String emailRequest = req.getParameter("email");
        String passwordRequest = req.getParameter("password");

        String user = req.getHeader("user");

        DeleteUserService deleteUserService = new DeleteUserService();
        String resultOfDeleteUser = deleteUserService.deleteUser(emailRequest, passwordRequest);

        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            writer.println(resultOfDeleteUser);
        } catch (IOException e) {
            System.out.println("Problem witch response");
            e.printStackTrace();
        }
    }
}
