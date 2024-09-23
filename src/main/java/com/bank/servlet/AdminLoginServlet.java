package com.bank.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.dao.AdminDAO;
import com.bank.model.Adminlogin;

@WebServlet("/adminlogin")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1;
    private AdminDAO loginDao;

    public void init() {
        loginDao = new AdminDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
//    	boolean is_authenticated=false;

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Adminlogin loginBean = new Adminlogin();
        loginBean.setUsername(username);
        loginBean.setPassword(password);

        try {
            if (loginDao.validate(loginBean)) {
//            	is_authenticated=true;
                HttpSession session = request.getSession();
                session.setAttribute("admin",username);
                response.sendRedirect("admindashboard.jsp");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("user", username);
                request.setAttribute("loginError","Incorrect password");
                response.sendRedirect("adminlogin.jsp");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}