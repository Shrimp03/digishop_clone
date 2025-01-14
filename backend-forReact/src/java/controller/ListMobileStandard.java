/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import model.Standard_packages;

/**
 *
 * @author ngotr
 */
@WebServlet(name = "ListMobileStandard", urlPatterns = {"/listmobilestandard"})
public class ListMobileStandard extends HttpServlet {

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
            out.println("<title>Servlet ListMobileStandard</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListMobileStandard at " + request.getContextPath() + "</h1>");
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
        try {
            AllDAOImpl mbs = new AllDAOImpl();
            String orderby = request.getParameter("orderby");
            String sqlParam = (orderby == null) ? "" : orderby;
            String pageValue = request.getParameter("page");// dat chi so tren api
            String limit = request.getParameter("limit");
            List<Standard_packages> list1 = mbs.getAllMobileStandards(sqlParam);
            int page, numberpage;
            int totalPagesize = list1.size();

//            String totalItems = request.getParameter("totalRows");
//            pagesize = (totalItems == null) ? list1.size():Integer.parseInt(totalItems);
            numberpage = (limit == null) ? totalPagesize : Integer.parseInt(limit);
            page = (pageValue == null) ? 1 : Integer.parseInt(pageValue);
            int start, end;
            start = (page - 1) * numberpage;
            end = Math.min(numberpage * page, totalPagesize);
            int totalPage;
            if(totalPagesize % numberpage == 0)
            {
                totalPage = (int) totalPagesize/numberpage;
            }
            else {
                totalPage = (int) (Math.ceil(totalPagesize/numberpage) + 1);
            }
            
                 
            List<Standard_packages> list = mbs.getMobileStandard(list1, start, end);
            JsonObject responseJson = new JsonObject();
            responseJson.add("data", new Gson().toJsonTree(list));
            JsonObject paginationJson = new JsonObject();
            paginationJson.addProperty("page", page);
            paginationJson.addProperty("totalPages", totalPage);
            paginationJson.addProperty("limit", numberpage);
            responseJson.add("pagination", paginationJson);
            out.write(responseJson.toString());

        } catch (Exception e) {
            e.printStackTrace();
            out.print(e.getMessage());
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
