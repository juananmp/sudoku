/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Matriz;

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.SQLException;

import java.util.logging.Level;

import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletContext;

import javax.servlet.ServletException;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 *
 * @author janto
 *
 */
public class loginDatabase extends HttpServlet {

    /**
     *
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     *
     * methods.
     *
     *
     *
     * @param request servlet request
     *
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     *
     * @throws IOException if an I/O error occurs
     *
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");

            out.println("<html>");

            out.println("<head>");

            out.println("<title>Servlet loginDatabase</title>");

            out.println("</head>");

            out.println("<body>");

            out.println("<h1>Servlet loginDatabase at " + request.getContextPath() + "</h1>");

            out.println("</body>");

            out.println("</html>");

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     *
     * Handles the HTTP <code>GET</code> method.
     *
     *
     *
     * @param request servlet request
     *
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     *
     * @throws IOException if an I/O error occurs
     *
     */
    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }

    /**
     *
     * Handles the HTTP <code>POST</code> method.
     *
     *
     *
     * @param request servlet request
     *
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     *
     * @throws IOException if an I/O error occurs
     *
     */
    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("user");

        String password = request.getParameter("password");

        String name = request.getParameter("name");

        Database login = new Database();

        //httpsesision? HttpSession session = request.getSession(); dentro del if  session.setAttribute("user",user) response.sendRedirect("./ProfileServlet ")
        ServletContext contexto = request.getServletContext();

        if (login.existeCuenta(user, password)) {
if(!name.equals("")){
                System.out.println("vacio");
            System.out.println("Entro");

//                RequestDispatcher anhadirServlet =
//                    //contexto.getNamedDispatcher("Matriz");
//                 //contexto.getRequestDispatcher("/login.html");
//                contexto.getNamedDispatcher("ServletCookie2");
//                  anhadirServlet.forward(request, response);
            Cookie ck = new Cookie("name", name);

            response.addCookie(ck);

            // out.println("<a href='./ServletCookie2'> ServletCookie2</a>"); 
            //mejor redirect que forwars porque con forward me daba error
            response.sendRedirect(request.getContextPath() + "/ServletCookie2");
            }else{
                        System.out.println("campo nulo");
                         RequestDispatcher paginaError =
                     contexto.getRequestDispatcher("/errorCookie.html");
             paginaError.forward(request, response);
                        }

        } else {

            System.out.println("No Entro");

            RequestDispatcher paginaError
                    = contexto.getRequestDispatcher("/error.html");

            paginaError.forward(request, response);

        }

        processRequest(request, response);

    }

    /**
     *
     * Returns a short description of the servlet.
     *
     *
     *
     * @return a String containing servlet description
     *
     */
    @Override

    public String getServletInfo() {

        return "Short description";

    }// </editor-fold>

}
