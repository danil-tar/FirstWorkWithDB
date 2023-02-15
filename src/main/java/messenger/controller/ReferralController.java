package messenger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import messenger.annotation.Autowired;
import messenger.annotation.RegisterServlet;
import messenger.menegment.InstanceFactory;
import messenger.service.ReferralService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

@RegisterServlet(url = "/referral")
public class ReferralController extends HttpServlet {

    private ReferralController() {
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        ReferralService referralService = InstanceFactory.getInstance(ReferralService.class);

        HashSet<String> referralEmails = referralService.getReferrals(email);
        ObjectMapper objectMapper = new ObjectMapper();
        String referralEmailsAsString = objectMapper.writeValueAsString(referralEmails);

        PrintWriter respWriter = resp.getWriter();
        respWriter.write(referralEmailsAsString);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer partnerId = Integer.parseInt(req.getParameter("partnerId"));
        String referralEmail = req.getParameter("referralEmail");
        InstanceFactory.getInstance(ReferralService.class).registrationAsReferral(partnerId, referralEmail);

        PrintWriter respWriter = resp.getWriter();
        respWriter.write("Referral added successfully");
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
req.getParameter("email");    }


}
