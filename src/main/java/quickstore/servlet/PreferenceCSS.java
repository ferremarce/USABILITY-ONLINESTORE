/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import quickstore.ejb.entity.Preference;
import quickstore.ejb.facade.PreferenceDAO;

/**
 *
 * @author jmferreira
 */
@WebServlet(name = "PreferenceCSS", urlPatterns = {"/PreferenceCSS"})
public class PreferenceCSS extends HttpServlet {

    @Inject
    PreferenceDAO preferenceDAO;

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
        response.setContentType("text/css");
        PrintWriter out = response.getWriter();
        String parametro = request.getParameter("object");
        String cadena;
        try {
            Preference p = preferenceDAO.find(Integer.parseInt(parametro));

            cadena = ".ui-widget,\n"
                    + ".ui-widget-header,\n"
                    + ".ui-widget-content,\n"
                    + ".ui-datatable ui-widget,\n"
                    + ".ui-datatable-data ui-widget-content,\n"
                    + ".ui-column-title,\n"
                    + ".ui-toolbar\n"
                    + "{\n"
                    + "    font-size: " + p.getTamanho() + " !important;\n"
                    + "}";
            out.println(cadena);
        } catch (Exception ex) {
            cadena = ".ui-widget,\n"
                    + ".ui-widget-header,\n"
                    + ".ui-widget-content,\n"
                    + ".ui-datatable ui-widget,\n"
                    + ".ui-datatable-data ui-widget-content,\n"
                    + ".ui-column-title,\n"
                    + ".ui-toolbar\n"
                    + "{\n"
                    + "    font-size: 12px !important;\n"
                    + "}";
            out.println(cadena);
        }
        out.close();
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
