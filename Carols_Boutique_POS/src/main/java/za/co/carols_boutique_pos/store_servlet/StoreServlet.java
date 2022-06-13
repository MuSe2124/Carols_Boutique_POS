/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package za.co.carols_boutique_pos.store_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.carols_boutique_pos.models.Store;
import za.co.carols_boutique_pos.rest_clients.RestStore;

/**
 *
 * @author muaad
 */
@WebServlet(name = "StoreServlet", urlPatterns = {"/StoreServlet"})
public class StoreServlet extends HttpServlet {

    private RestStore rs;

    public StoreServlet() {
        rs = new RestStore();
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
                Store store = new Store(request.getParameter("storeID"), request.getParameter("password"));
                String loginResponseMessage = rs.loginStore(request.getParameter("storeID"), request.getParameter("password"));
                if (store != null) {
                    HttpSession session = request.getSession();//blank=returns session, doesnt exist itll create one for you//true=if session exists, still creates new session//false= not new session, gets existing session
                    session.setAttribute("store", store);
                    request.getRequestDispatcher("Index.jsp").forward(request, response);
                } else {
                    request.setAttribute("loginResponseMessage", loginResponseMessage);
                    request.getRequestDispatcher("LoginStore.jsp").forward(request, response);
                }
                break;
            case "register":
                Store u = new Store(request.getParameter("name"), request.getParameter("location"), request.getParameter("password"));
                String registerResponseMessage = rs.registerStore(u);
                if (u != null) {
                    request.setAttribute("store", u);
                    request.getRequestDispatcher("LoginStore.jsp").forward(request, response);
                } else {
                    request.setAttribute("registerResponseMessage", registerResponseMessage);
                    request.getRequestDispatcher("RegisterStore.jsp").forward(request, response);
                }
                break;
        }
    }
}
