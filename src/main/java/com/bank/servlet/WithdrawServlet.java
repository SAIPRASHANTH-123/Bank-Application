package com.bank.servlet;

import com.bank.dao.CustomerDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/WithdrawServlet")

public class WithdrawServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		
		HttpSession session = request.getSession();
        Integer accountNo = (Integer) session.getAttribute("accountNo");
        
		double amount = Double.parseDouble(request.getParameter("amount"));
		
		
		CustomerDAO customerDAO = new CustomerDAO();
		boolean sucsess = customerDAO.withdraw(accountNo,amount);
		
		if(sucsess) {
			response.getWriter().println("Withdrawl Sucessfull");
		}
		else {
			response.getWriter().println("Withdrawl Failed!");
		}
	}
	
}
