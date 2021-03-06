/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Matriz;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
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
        Database db = new Database();

        int[][] matriz = db.plantilla("Plantilla1");
     

        
        HttpSession cliente = request.getSession();
        String comprobar = request.getParameter("comprobar"); //Saber si nos piden comprobar, esto siempre escucha y siempre va a ser null hasta que pulse el botón de comprobar 
          
        //la primera vez que entre el valor de numeroYposicion va a ser nulo
        if (cliente.getAttribute("numeroYposicion") != null) {//si existe el numeroYposcion es que la partida ha empezado
            
            for (int m = 0; m < 9; m++) { //movernos por fila y columna 1er piso
                for (int k = 0; k < 9; k++) { //entramos por habitcion del primer piso
                    if (comprobar(request.getParameter("numero" + m + k))) {//¿Nos envian algo? y si es un número?
                        //cp los valores que existen y los sobreescribes con los nuevos
                        //vas habitación por habitación y ves que hay un número en ella, cuando vuelve y dice ya no esta este numero lo sobreescribimos
                        int[][] numeroYposicion = (int[][]) cliente.getAttribute("numeroYposicion");
                        numeroYposicion[m][k] = Integer.parseInt(request.getParameter("numero" + m + k));
                        request.setAttribute("numeroYposicion", numeroYposicion); //aqui se subscribe
                        
                    }

                }
            }
            //no hay casa la creo
        } else { // si no existe numeroYposcion lo inicilazimos
            //se guardan las respuestas del cliente en un int bidimensional
            int[][] numeroYposicion = new int[9][9];
            //nada mas crear la casa lo lleno de 0s que el usuario no ve
            cliente.setAttribute("numeroYposicion", numeroYposicion);
        }

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Matriz</title>");
            out.println("<link rel=\"stylesheet\" href=\"./Css/cssTabla.css\">");
            out.println("</head>");
            out.println("<body>");
           
            out.println(" <form method=\"post\" action=\"/Sudoku/Matriz\" name=\"datos\"><table id=\"grid\">");

//cogemos otra vez la matriz, volvemos a coger la casa, puede ser nueva o ya con datos
            int[][] contenido = (int[][]) cliente.getAttribute("numeroYposicion");
//estos 2 bucles nos sirven para insertar los inputs, las cajas de texto
//volvemos a recorrer por piso y las habitaciones
            for (int i = 0; i < matriz.length; i++) {//matriz es matriz plantilla
                //tr fila
                out.println("  <tr>");
                for (int j = 0; j < matriz.length; j++) {
                    //coumna; fijo la fila y ahora voy de columna en columna
                    if (matriz[i][j] != 0) { //si es distinto de 0 no se puede escribir
                        out.println("<td  class=\"cell\"> <input type=\"text\" value=\"" + matriz[i][j] + "\" disabled></td>"); //como ya hemos definido que matriz es la plantilla queremos que inicialmente esos valores esten bloqueados
                    } else {//si es 0 es que ahi deemos escribir
                        //dentro del else comprobamos si hemos escrito algo ya o no y si decidimos comprobar pintarlo en verde o rojo
         //si casa no esta vacia y la habitacion no tiene 0
                        if (contenido != null && contenido[i][j] != 0) {
                            //comprobar es saber si es un número 
                            // && contenido[i][j]<1 && contenido[i][j]>9
                            if (comprobar(Integer.toString(contenido[i][j]))&& contenido[i][j]>0 && contenido[i][j]<10) { 
                                if(comprobar!=null){//Si nos han pedido comprobar pinto
                                out.println("<td  class=\"cell\"><input style=\"background:"+resolver(i, j, contenido[i][j], db.plantilla("solucion1"))+";\" type=\"text\" name=\"numero" + i + "" + j + "\" value=\"" + contenido[i][j] + "\"></td>");
                                }else{//Si no nos piden comprobar NO pinto
                                out.println("<td  class=\"cell\"><input type=\"text\" name=\"numero" + i + "" + j + "\" value=\"" + contenido[i][j] + "\"></td>");
                                }
                                }else{
                                out.println("<td  class=\"cell\"><input type=\"text\" name=\"numero" + i + "" + j + "\"></td>");
                            }
                        } else { // sino imprime la caja de texto con el 0
                            out.println("<td  class=\"cell\"><input type=\"text\" name=\"numero" + i + "" + j + "\"></td>");
                        }
                    }
                }
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<button>Guardar</button></form>");
             
            out.println("<form method=\"post\" action=\"/Sudoku/Matriz\" name=\"datos\"><input type=\"hidden\" name=\"comprobar\" value=\"algo\"><button>Comprobar</button></form>");
            ServletContext ctx = getServletContext();
        int totalUsers = (Integer) ctx.getAttribute("totalusers");
        int currentUsers = (Integer) ctx.getAttribute("currentusers");
       
        out.print("<br>total users= " + totalUsers);
        out.print("<br>current users= " + currentUsers);
            out.print("<br>");
            out.print("<br>");
            out.println("  <a href=\"/Sudoku/logOut\">Log Out</a>");
            out.println("</body>");
            out.println("</html>");
        }
        // meterselo a un botón 
        //request.logout();
        //
        
            //cliente.invalidate();
    }

    
    public String resolver(int x,int y, int resp, int[][] solucion) {
        if(solucion[x][y]==resp){
            return "green";
        }else{
            return "red";
        }
    }

    /**
     *      * Handles the HTTP <code>GET</code> method.      *      * @param
     * request servlet request      * @param response servlet response      
     *
     *
     * @throws ServletException if a servlet-specific error occurs      
     *
     * @throws IOException if an I/O error occurs      
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }

    /**
     *      * Handles the HTTP <code>POST</code> method.      *      * @param
     * request servlet request      * @param response servlet response      
     *
     *
     * @throws ServletException if a servlet-specific error occurs      
     *
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
