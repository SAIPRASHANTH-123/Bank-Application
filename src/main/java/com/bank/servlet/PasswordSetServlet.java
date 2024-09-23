package com.bank.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.CustomerDAO;

@WebServlet("/SetupPasswordServlet")
public class PasswordSetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountNo = Integer.parseInt(request.getParameter("accountNo"));
        String tempPassword = request.getParameter("tempPassword");
        String newPassword = request.getParameter("newPassword");
        
        CustomerDAO customerDAO = new CustomerDAO();
        boolean success = customerDAO.setupNewPassword(accountNo, tempPassword, newPassword);

        if (success) {
        	customerDAO.changeLoginStatus(accountNo);
            response.getWriter().println("Password setup successful!");
            
        } else {
            response.getWriter().println("Password setup failed!");
        }
    }
}
