package com.bank.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.model.Customerlogin;
import com.bank.dao.CustomerDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountNo = Integer.parseInt(request.getParameter("accountNo"));
        String password = request.getParameter("password");

        CustomerDAO customerDAO = new CustomerDAO();
        Customerlogin customer = customerDAO.authenticateCustomer(accountNo, password);

        if (customer != null) {
        	HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            session.setAttribute("accountNo", accountNo);
            
            if (customerDAO.checkfirstLogin(accountNo)) {
                response.sendRedirect("passwordsetup.jsp");
            } else {
            	
                response.sendRedirect("customerdashboard.jsp");
            }
        } else {
            response.getWriter().println("Login failed!");
        }
    }
}
