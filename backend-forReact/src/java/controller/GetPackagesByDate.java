/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dal.AllDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.All_Packages;
import model.Child_packages;
import model.Standard_packages;

/**
 *
 * @author ngotr
 */
@WebServlet(name = "GetMobileChildByDate", urlPatterns = {"/getpackagesbydate"})
public class GetPackagesByDate extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetMobileChildByDate</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetMobileChildByDate at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        try{
            String name = request.getParameter("package_name");
            String id = request.getParameter("package_id");
            AllDAOImpl c = new AllDAOImpl();
            List<All_Packages> list = new ArrayList<>();
            List<Standard_packages> listStandard = c.getAllMobileStandards("");
            List<Child_packages> listChild = c.getAllChild();
            for (Standard_packages x : listStandard) {
                All_Packages p = new All_Packages(x.getStandard_id(), x.getStandard_name(), x.getStandard_price(), x.getStandard_timelimit(), x.getDetail_id(), x.getDetail_treatment(), x.getDetail_cancel(), x.getDetail_check(), x.getDetail_legacy(), x.getDetail_extension(), x.getDetail_hotline(), x.getDetail_enrollment());
                list.add(p);
            }
            for (Child_packages x : listChild) {
                All_Packages p = new All_Packages(x.getStandard_id(), x.getChild_name(), x.getChild_price(), x.getChild_timeLimit(), x.getDetail_id(), x.getDetail_treatment(), x.getDetail_cancel(), x.getDetail_check(), x.getDetail_legacy(), x.getDetail_extension(), x.getDetail_hotline(), x.getDetail_enrollment());
                list.add(p);
            }
            Gson gson = new Gson();
            for(All_Packages x : list)
            {
                if(x.getPackages_name().equals(name) && x.getPackages_id().equals(id))
                {
                    String json = gson.toJson(x);
                    out.write(json);
                    break;
                }  
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            out.print(e);
        }
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
