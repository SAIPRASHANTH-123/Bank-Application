package com.bank.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.dao.CustomerDAO;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer accountNo = (Integer) session.getAttribute("accountNo");

        double amount = Double.parseDouble(request.getParameter("amount"));

        CustomerDAO customerDAO = new CustomerDAO();
        boolean success = customerDAO.deposit(accountNo, amount);

        if (success) {
            response.getWriter().println("Deposit successful!");
        } else {
            response.getWriter().println("Deposit failed!");
        }
    }
}
