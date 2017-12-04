/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Matriz;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author janto
 */
public class ReadSudokuServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
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
            out.println("<link rel=\"stylesheet\" href=\"./Css/cssTabla.css\">");
            out.println("<title>Servlet ReadSudokuServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            try(Connection  conn = connection();
            Statement stat = conn.createStatement()){
                        //the execute method of the statement object will return a boolean value after executing the query string
            boolean hasResulSet = stat.execute("SELECT * FROM plantilla");
                        //we will check for a boolean true before n  the query rsults in a result set object 
                        if(hasResulSet){
                //if the condition is true then we create a result set object to store the results  of the query by calling
                
                //en el result guardamos la query
                ResultSet result = stat.getResultSet();
            //the method get result set
            
            //display data
                // out.println("<table class=\"sudoku1\">");
//                  out.println(" <form method=\"post\" action=\"/Sudoku/ReadSudokuServlet\" name=\"datos\">"
//                          + "<table id=\"grid\" border=\"1\" style=\"border-collapse: collapse;>");
                  
out.println("<table id=\"grid\" >");
                //out.println("<link href= src/web/Css/cssTabla.css" +"rel=\"stylesheet\" type=\"text/css\">");
                
                
                while(result.next()){
                     
                  //  out.println(" <form method=\"post\" action=\"/Sudoku/ReadSudokuServlet\" name=\"datos\"><table id=\"grid\">");
                    out.println("<tr>");
                   // out.println("<td><input type=\"text\" name=\"numero"+result.getInt("columna1")+ "\" value=\""+  "</ td>");
//                    out.println("<td>"+result.getInt("columna2")+ "</td>");
//                    out.println("<td>"+result.getInt("columna3")+ "</td>");
//                    out.println("<td>"+result.getInt("columna4")+ "</td>");
//                    out.println("<td>"+result.getInt("columna5")+ "</td>");
//                    out.println("<td>"+result.getInt("columna6")+ "</td>");
//                    out.println("<td>"+result.getInt("columna7")+ "</td>");
//                    out.println("<td>"+result.getInt("columna8")+ "</td>");
//                    out.println("<td>"+result.getInt("columna9")+ "</td>");    
                     out.println("<td class=\"cell\"><input type=\"text\" maxlength=\"1\" name=\"numero" + "\" value=\"" + result.getInt("columna1") + "\"></td>");
                     out.println("<td class=\"cell\"><input type=\"text\" maxlength=\"1\" name=\"numero" + "\" value=\"" + result.getInt("columna2") + "\"></td>");
                     out.println("<td class=\"cell\"><input type=\"text\" maxlength=\"1\" name=\"numero" + "\" value=\"" + result.getInt("columna3") + "\"></td>");
                     out.println("<td class=\"cell\"><input type=\"text\" maxlength=\"1\" name=\"numero" + "\" value=\"" + result.getInt("columna4") + "\"></td>");
                     out.println("<td class=\"cell\"><input type=\"text\" maxlength=\"1\" name=\"numero" + "\" value=\"" + result.getInt("columna5") + "\"></td>");
                     out.println("<td class=\"cell\"><input type=\"text\" maxlength=\"1\" name=\"numero" + "\" value=\"" + result.getInt("columna6") + "\"></td>");
                     out.println("<td class=\"cell\"><input type=\"text\" maxlength=\"1\" name=\"numero" + "\" value=\"" + result.getInt("columna7") + "\"></td>");
                     out.println("<td class=\"cell\"><input type=\"text\" maxlength=\"1\" name=\"numero" + "\" value=\"" + result.getInt("columna8") + "\"></td>");
                     out.println("<td class=\"cell\"><input type=\"text\" maxlength=\"1\" name=\"numero" + "\" value=\"" + result.getInt("columna9") + "\"></td>");
                   
                    //out.println("<br>");
                    out.println("</tr>");
                    
                }
                out.println("</table>");
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
            out.println("<h1>Servlet ReadSudokuServlet at " + request.getContextPath() + "</h1>");
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


   
    // create a connection to the database
    
    private static Connection connection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/sudoku2?verifyServerCertificate=false&useSSL=true", "root", "root");
            
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
            return null;
        }
    }
    
}
