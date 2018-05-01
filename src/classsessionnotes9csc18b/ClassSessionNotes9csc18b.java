/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsessionnotes9csc18b;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author rcc
 */
public class ClassSessionNotes9csc18b {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        
        //System.out.println("Enter name of file to write: ");
        //String filename = kb.nextLine();
        
        //hardcode the file path (required: \\ is an escape sequence).
        String filename = "C:\\users\\rcc\\Desktop\\myfile.txt";
        
        try{
            
            PrintWriter output = new PrintWriter(new File(filename));
            
            
            for (int i=1; i<10; i++){
                //output.println(i);//Creates a file with the numbers 1-10 written on a new line.
                output.printf("%5d%n", i); //Writes 1-10 on new line right justified.
            }
            
            //close the stream.
            output.close();
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        //Writing the file back in.
        System.out.println("Enter the name of the file back in: ");
        String filename2 = kb.nextLine();
        
        try{
            
            Scanner inputFile = new Scanner(new FileReader(filename));
            while(inputFile.hasNext()){
                int value = inputFile.nextInt();
                System.out.println(value);
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
    }//End main.
    
}//End class.
