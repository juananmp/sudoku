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

/**
 *
 * @author janto
 */
public class ReadSudoku {
    
    private String empname;
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
             empname="";
             String line;
             line = input.nextLine();
             
             try(Scanner data = new Scanner(line)){
                 while(!data.hasNextInt()){
                     empname += data.next()+ "";
                 }
                 empname = empname.trim();

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
//             System.out.println(empname+"\t"+ columna1 +"\t"+ columna2+ "\t"+columna3+ "\t"+columna4+ "\t"+columna5+ "\t"+columna6+ "\t"+columna7+ "\t"+columna8
//             + "\t"+columna9);
            saveData(); //call the method to save data into the database
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
            pstat.setString(1, empname);
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
    
    
    // create a connection to the database
    
    private Connection connection(){
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
            }catch(Exception e){
                System.out.println(e);
            }
        }
            
        }
