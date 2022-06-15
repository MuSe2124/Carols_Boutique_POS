/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package za.co.carols_boutique_pos.employee_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.carols_boutique_pos.models.Employee;
import za.co.carols_boutique_pos.rest_clients.RestEmployee;

/**
 *
 * @author muaad
 */
@WebServlet(name = "EmployeeServlet", urlPatterns = {"/EmployeeServlet"})
public class EmployeeServlet extends HttpServlet {
    private RestEmployee re;
    
    public EmployeeServlet(){
        re = new RestEmployee();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        switch (request.getParameter("submit")) {
            case "login":
                Employee emp = new Employee(request.getParameter("employeeID"), request.getParameter("password"), request.getParameter("storeID"));
                String loginResponseMessage = re.login(request.getParameter("employeeID"), request.getParameter("password"), request.getParameter("storeID"));
                if (emp != null) {
                    HttpSession session = request.getSession();//blank=returns session, doesnt exist itll create one for you//true=if session exists, still creates new session//false= not new session, gets existing session
                    session.setAttribute("employee", emp);
                    request.getRequestDispatcher("Index.jsp").forward(request, response);
                } else {
                    request.setAttribute("loginResponseMessage", loginResponseMessage);
                    
                    request.getRequestDispatcher("LoginEmployee.jsp").forward(request, response);
                }
                break;
            case "register":
                Employee e = new Employee(request.getParameter("name"), request.getParameter("password"), request.getParameter("storeID"));
                String registerResponseMessage = re.register(e);  
                if (e != null) {
                    request.setAttribute("employee", e);
                    request.getRequestDispatcher("LoginEmployee.jsp").forward(request, response);
                } else {
                    request.setAttribute("registerResponseMessage", registerResponseMessage);
                    request.getRequestDispatcher("RegisterEmployee.jsp").forward(request, response);
                }
                break;
    }
    }
    
}
