/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountDetailDAO;
import entity.AccountDetail;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HUY
 */
@WebServlet(name = "EditProfile", urlPatterns = {"/EditProfile"})
public class EditProfile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String service = request.getParameter("service");

            //View
            if (service.equalsIgnoreCase("view")) {
                int id = Integer.parseInt(request.getParameter("id"));
                AccountDetailDAO accDetalDAO = new AccountDetailDAO();
                AccountDetail accDetail = accDetalDAO.getAccountById(id);
                request.setAttribute("accDetail", accDetail);
                request.getRequestDispatcher("editProfile.jsp").forward(request, response);
            }

            //Edit
            if (service.equalsIgnoreCase("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String phone = request.getParameter("phone");
                int gender = Integer.parseInt(request.getParameter("gender"));
                String address = request.getParameter("address");
                AccountDetail accDetail = new AccountDetail(id, name, phone, gender, address);
                AccountDetailDAO accDetailDAO = new AccountDetailDAO();
                if (accDetailDAO.editProfile(accDetail)) {
                    request.setAttribute("message", "Edit Profile Successfully");
                    request.getRequestDispatcher("home").forward(request, response);
                } else {
                    request.setAttribute("message", "Edit Profile Fail");
                    request.getRequestDispatcher("EditProfile?service=view&id="+id+"").forward(request, response);
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}