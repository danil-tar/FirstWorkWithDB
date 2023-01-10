package messenger.controller;

import messenger.dto.User;
import messenger.service.DeleteUserService;
import messenger.service.JWTService;

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

        String token = req.getHeader("Jwt");

        JWTService jwtService = new JWTService();

        User user = jwtService.testValidity(token);

        PrintWriter writer = null;

        if (user != null) {
            DeleteUserService deleteUserService = new DeleteUserService();
            String resultOfDeleteUser = deleteUserService.deleteUser(user.getEmail());
            try {
                writer = resp.getWriter();
                writer.println(resultOfDeleteUser);
                resp.addHeader("result of deleting", "true");
                writer.close();
            } catch (IOException e) {
                System.out.println("Problem witch response");
                e.printStackTrace();
            }
        } else {
            resp.addHeader("result of deleting", "false");
        }
    }
}
