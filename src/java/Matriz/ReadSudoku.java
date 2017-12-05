/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Matriz;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author janto
 */
public class ReadSudoku {
    
    private String fila;
    private int columna1;
    private int columna2;
    private int columna3;
    private int columna4;
    private int columna5;
    private int columna6;
    private int columna7;
    private int columna8;
    private int columna9;
    
    
    public void readData(){
     try(Scanner input = new Scanner(new File("src/java/Matriz/sudoku1.txt"))){
         
         //vamos a leer el fichero linea por linea
         while (input.hasNextLine()) {
             //chequea si hay una linea para leer sino entonces para
             fila="";
             String line;
             line = input.nextLine();
             
             //if the line variable has no data then re-iterative the loop to move on the next line
             if(line.length() <= 0)
                 continue;
             
             try(Scanner data = new Scanner(line)){
                 while(!data.hasNextInt()){
                     fila += data.next()+ "";
                 }
                 fila = fila.trim();

                 //get columnas
                 if(data.hasNextInt()){
                     columna1 = data.nextInt();
                 }
                  if(data.hasNextInt()){
                     columna2 = data.nextInt();
                 }
                   if(data.hasNextInt()){
                     columna3 = data.nextInt();
                 }
                   if(data.hasNextInt()){
                     columna4 = data.nextInt();
                 }if(data.hasNextInt()){
                     columna5 = data.nextInt();
                 }if(data.hasNextInt()){
                     columna6 = data.nextInt();
                 }if(data.hasNextInt()){
                     columna7 = data.nextInt();
                 }if(data.hasNextInt()){
                     columna8 = data.nextInt();
                 }if(data.hasNextInt()){
                     columna9 = data.nextInt();
                 }
             }
             //imprimir por pamtalla la tabla del fichero txt
//             System.out.println(fila+"\t"+ columna1 +"\t"+ columna2+ "\t"+columna3+ "\t"+columna4+ "\t"+columna5+ "\t"+columna6+ "\t"+columna7+ "\t"+columna8
//             + "\t"+columna9);
           // saveData(); //call the method to save data into the database
             //imprimir por pamtalla la tabla del fichero txt
//             System.out.println(empname+"\t"+ columna1 +"\t"+ columna2+ "\t"+columna3+ "\t"+columna4+ "\t"+columna5+ "\t"+columna6+ "\t"+columna7+ "\t"+columna8
//             + "\t"+columna9);
            //saveData(); //call the method to save data into the database
         }
         //check data
         //Scanner object finishes
         
     }   catch(IOException e){
         
         System.out.println(e);
     }
    }
    
    //private
    private void saveData(){
        try(Connection conn = connection();
                PreparedStatement pstat = conn.prepareStatement("INSERT INTO plantilla VALUES (?,?,?,?,?,?,?,?,?,?)")){
            
            //siempre se empieza desde 1
            pstat.setString(1, fila);
            pstat.setInt(2, columna1);
           pstat.setInt(3, columna2);
           pstat.setInt(4, columna3);
            pstat.setInt(5, columna4);
            pstat.setInt(6, columna5);
            pstat.setInt(7, columna6);
            pstat.setInt(8, columna7);
            pstat.setInt(9, columna8);
            pstat.setInt(10, columna9);
             pstat.executeUpdate();
            //after setting de parameters we can execute the prepareStatement by invoqing execute query method, returns an integer
           


        }catch(SQLException e){
            System.out.println(e);
            
        }
       
    }
    //ahora creo un método que recupera los datos de plantilla de la BBDD
    public static void displayData(){
        //using a try with resources statement we open a connection to the database by calling
        //the connect method that i created earlier we also create a statement object for executing sql statements
        try(Connection  conn = connection();
            Statement stat = conn.createStatement()){
                        //the execute method of the statement object will return a boolean value after executing the query string
            boolean hasResulSet = stat.execute("SELECT * FROM plantilla");
                        //we will check for a boolean true before saving the query rsults in a result set object 
                        if(hasResulSet){
                //if the condition is true then we create a result set object to store the results  of the query by calling
                
                //en el result guardamos la query
                ResultSet result = stat.getResultSet();
            //the method get result set
            
//            ResultSetMetaData metaData = result.getMetaData();
//            int columnCount = metaData.getColumnCount();
//
//            for (int i=1; i<=columnCount; i++){
//                System.out.println(metaData.getColumnLabel(i)+"\t\t");
//            }
//                System.out.println();

                //display data
                while(result.next()){
                    //printf OJO no pritln para formata a los datos de salida ahora comentaremos el ssaveData
                    //primer % en esa posición se va escribir un valor el valor se encuentra entre las comillas la s:string d:caracter entero nº indica los decimales
                    //%n salto de línea
                    //la composción fila-column1-... saltamos la linea (linea anterior) y continuamos con el .next y asi imprimimos todo el sudoku
                    System.out.printf("%-20s%4d%4d%4d%4d%4d%4d%4d%4d%4d%n",result.getString("fila"),result.getInt("columna1"),
                            result.getInt("columna2"),result.getInt("columna3"),result.getInt("columna4"),result.getInt("columna5"),
                            result.getInt("columna6"),result.getInt("columna7"),result.getInt("columna8"),result.getInt("columna9"));
                    
                    
                }
                
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }
    
    
    
    
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
class FDemo{//esta clase va a crear un objeto
        public static void main(String[] args){
            ReadSudoku rsu = new ReadSudoku();
            
            try{
                rsu.readData();
                rsu.displayData();
            }catch(Exception e){
                System.out.println(e);
            }
        }
            
        }
