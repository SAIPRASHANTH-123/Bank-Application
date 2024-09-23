package com.bank.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.*;

@WebServlet("/CustomerDetailsServlet")
public class CustomerDetailsServlet extends HttpServlet {
    private static final String EDIT_CUSTOMER_SQL = "SELECT * FROM Customer WHERE account_no = ?";
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int account_no = Integer.parseInt(request.getParameter("accountno"));
		
		AdminDAO admin = new AdminDAO();
		try(Connection connection = admin.getConnection();
				PreparedStatement CustomerDetailsStmt = connection.prepareStatement(EDIT_CUSTOMER_SQL)){
			
			CustomerDetailsStmt.setInt(1, account_no);
			
			ResultSet rs = CustomerDetailsStmt.executeQuery();
			
			if(rs.next()) {
				request.setAttribute("account_no", rs.getInt("account_no"));
				request.setAttribute("full_name", rs.getString("full_name"));
				request.setAttribute("address", rs.getString("address"));
				request.setAttribute("mobile_no", rs.getDouble	("mobile_no"));
				request.setAttribute("email_id", rs.getString("email_id"));
				request.setAttribute("account_type", rs.getString("account_type"));
				request.setAttribute("date_of_birth", rs.getDate("date_of_birth"));
				request.setAttribute("id_proof", rs.getString("id_proof"));
				
				request.getRequestDispatcher("editcustomer.jsp").forward(request, response);
//				response.sendRedirect("EditCustomerDetails.jsp");
			}
			else {
				response.getWriter().println("Customer not found");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
