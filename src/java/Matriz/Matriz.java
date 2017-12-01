/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Matriz;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author janto
 */
public class Matriz extends HttpServlet {

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

        int[][] matriz = {{0, 6, 0, 1, 0, 4, 0, 5, 0}, {0, 0, 8, 3, 0, 5, 6, 0, 0}, {2, 0, 0, 0, 0, 0, 0, 0, 1}, {8, 0, 0, 4, 0, 7, 0, 0, 6}, {0, 0, 6, 0, 0, 0, 3, 0, 0}, {7, 0, 0, 9, 0, 1, 0, 0, 4}, {5, 0, 0, 0, 0, 0, 0, 0, 2}, {0, 0, 7, 2, 0, 6, 9, 0, 0}, {0, 4, 0, 5, 0, 8, 0, 7, 0}};
        boolean comenzar = false;

        HttpSession cliente = request.getSession();
        if (cliente.getAttribute("numeroYposicion") != null) {//si existe el numeroYposcion es que la partida ha empezado
            System.out.println("ENTROOOO");
            comenzar = true; //ya hemos hemos empezado a jugar hemos, ya hay algo que guardar
            for (int m = 0; m < 9; m++) { //movernos por fila y columna
                for (int k = 0; k < 9; k++) {
                    if (comprobar(request.getParameter("numero" + m + k))) {//¿Nos envian algo?
                        //cp los valores que existen y los sobreescribes con los nuevos
                        int[][] numeroYposicion = (int[][]) cliente.getAttribute("numeroYposicion");
                        numeroYposicion[m][k] = Integer.parseInt(request.getParameter("numero" + m + k));
                        request.setAttribute("numeroYposicion", numeroYposicion); //aqui se subscribe
                        System.out.println("NUMERO------------------->" + request.getParameter("numero" + m + k));
                        int[][] aux = (int[][]) cliente.getAttribute("numeroYposicion");
                        System.out.println("NUMERO Y POSICION------------------->" + aux[m][k]);
                    }

                }
            }
        } else { // si no existe lo inicilazimos
            int[][] numeroYposicion = new int[9][9];
            cliente.setAttribute("numeroYposicion", numeroYposicion);
        }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Matriz</title>");
            out.println("</head>");
            out.println("<body>");
            // out.println("numero:"+numero);
//for (int x=0; x < matriz.length; x++) {
//out.println("|");
            out.println(" <form method=\"post\" action=\"/Sudoku/Matriz\" name=\"datos\"><table id=\"grid\">");
//out.println("");
//for (int y=0; y < matriz[x].length; y++) {
//out.println(matriz[x][y]);
//cogemos otra vez la matriz
            int[][] contenido = (int[][]) request.getAttribute("numeroYposicion");
//estos 2 bucles nos sirven para insertar los inputs, las cajas de texto
            for (int i = 0; i < matriz.length; i++) {
                out.println("  <tr>");
                for (int j = 0; j < matriz.length; j++) {
                    if (matriz[i][j] != 0) { //si es distinto de 0 no se puede escribir
                        out.println("<td><input type=\"text\" value=\"" + matriz[i][j] + "\" disabled></td>");
                    } else {//si es 0 es que ahi deemos escribir
                        if (cliente.getAttribute("numeroYposicion") != null && comenzar) {
                            if (comprobar(Integer.toString(contenido[i][j]))) { //si hay contenido y ha 
//comenzado la aprtida entonces ya empezamos a insertar los valores guardados
                                out.println("<td><input type=\"text\" name=\"numero" + i + "" + j + "\" value=\"" + contenido[i][j] + "\"></td>");

                            }
                        } else { // sino imprime la caja de texto con el 0
                            out.println("<td><input type=\"text\" name=\"numero" + i + "" + j + "\"></td>");
                        }
                    }
                }
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<button>Enviar</button></form>");
            out.print("<br>");
            out.println("<h1>Servlet Matriz at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     *      * Handles the HTTP <code>GET</code> method.      *      * @param
     * request servlet request      * @param response servlet response      *
     * @throws ServletException if a servlet-specific error occurs      *
     * @throws IOException if an I/O error occurs      
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     *      * Handles the HTTP <code>POST</code> method.      *      * @param
     * request servlet request      * @param response servlet response      *
     * @throws ServletException if a servlet-specific error occurs      *
     * @throws IOException if an I/O error occurs      
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     *      * Returns a short description of the servlet.      *      * @return
     * a String containing servlet description      
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public boolean comprobar(String celda) {
        try { //saber si es un numero lo que me manda
            Integer.parseInt(celda);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

}
