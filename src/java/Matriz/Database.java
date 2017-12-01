/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Matriz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author janto
 */
public class Database extends HttpServlet {

   DataSource datasource;
   
   String user;
   String password;
    
    //For this example you need to create the Mysql Database and the table
    //CREATE TABLE PERSONAS (         NOMBRE VARCHAR(100),          EDAD INT); 
    
    @Override
    public void init() throws ServletException {
        try {
            InitialContext initialContext = new InitialContext();
            datasource = (DataSource) initialContext.lookup("jdbc/sudoku2");
        } catch (NamingException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Listar Personas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de las personas:</h1>");
            out.println("<ul>");

            String query = null;
            query = "select *" + "from login";
            ResultSet resultSet = null;
            Statement statement = null;
            Connection connection = null;
            try {

                connection = datasource.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    out.println("<li>" + " user: "+ resultSet.getString("user")
                            + " password: " + resultSet.getString("password") + "</li>");
                }

            } catch (SQLException ex) {
                gestionarErrorEnConsultaSQL(ex, request, response);
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Database.class.getName()).log(Level.SEVERE,
                                "No se pudo cerrar el Resulset", ex);
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
        String nombre = request.getParameter("nombre");
        int edad;
        try {
            edad = Integer.parseInt(request.getParameter("edad"));
        } catch (NumberFormatException e) {
            edad = -1;
        }
        ServletContext contexto = request.getServletContext();
        String query = null;

        query = "insert into PERSONAS values('"
                + nombre + "'," + edad + ")";
        Statement statement = null;
        Connection connection = null;
        try {
            connection = datasource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);

            request.setAttribute("nextPage", this.getServletContext().getContextPath() + "/Database");
            RequestDispatcher paginaAltas
                    = contexto.getRequestDispatcher("/Ejemplo10/amigoInsertado.jsp");
            paginaAltas.forward(request, response);
        } catch (SQLException ex) {
            gestionarErrorEnConsultaSQL(ex, request, response);
        } finally {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private void gestionarErrorEnConsultaSQL(SQLException ex, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        ServletContext contexto = request.getServletContext();
        Logger
                .getLogger(Database.class
                        .getName()).log(Level.SEVERE, "No se pudo ejecutar la consulta contra la base de datos", ex);
        request.setAttribute("nextPage", this.getServletContext().getContextPath() + "/Ejemplo10/crearPersona.html");
        request.setAttribute("error", ex);
        request.setAttribute("errorMessage", ex.getMessage());
        Logger
                .getLogger(Database.class
                        .getName()).log(Level.INFO, "Set " + request.getAttribute("errorMessage"));

        RequestDispatcher paginaError = contexto.getRequestDispatcher("/Ejemplo10/errorSQL.jsp");
        paginaError.forward(request, response);
    }
    public boolean existeCuenta(String user, String password) throws SQLException{
         Connection connection = null;  
          String sql = "SELECT * FROM login WHERE user='"+user+"' AND password='"+password+"'";
          connection = datasource.getConnection();
          Statement st = connection.prepareStatement(sql);
          ResultSet rs = st.executeQuery(sql);
          return rs.next();
              
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
