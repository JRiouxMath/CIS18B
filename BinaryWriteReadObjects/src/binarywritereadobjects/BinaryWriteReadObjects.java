/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarywritereadobjects;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BinaryWriteReadObjects {

    public static void main(String[] args) {
        System.out.println("Creating object named 'a' of the type SomeClass...");
        SomeClass a = new SomeClass();
        
        System.out.println("Creating binary file called: some_class.dat...");
        try (ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("some_class.dat"))) {
                outFile.writeObject(a);
                System.out.println("Writing binary from SomeClass object 'a' to file: some_class.dat...");
                outFile.close();
                System.out.printf("File: some_class.dat successfully written.");                        
        }        
        catch(IOException e)
        {
            System.out.printf("Exception: %s caught%n",e.getMessage());
        }
        
        ////////////////////////////////////////////////////////////////////
        /// Now, let's read it back in, and put binary data into another ///
        /// object and compare...                                        ///
        ////////////////////////////////////////////////////////////////////
        try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("some_class.dat"))) {
            SomeClass b = (SomeClass)inFile.readObject();//type cast because reads in as type Object (doesn't know what blob of data is)
            inFile.close();
            System.out.printf("Binary content of the some_class.dat file:%n%n%s%n", b.toString());
        }        
        catch(IOException e)
        {
            System.out.printf("Exception: %s caught%n",e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BinaryWriteReadObjects.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}