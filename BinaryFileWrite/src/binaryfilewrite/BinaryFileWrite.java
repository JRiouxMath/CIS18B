/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binaryfilewrite;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BinaryFileWrite {

    public static void main(String[] args) {
        // this sample program creates a binary file called binary_data.dat
        // use: https://hexed.it/ to help examine binary files.
        
        try (ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("binary_data.dat"))) {
                outFile.write(110); // write binary representation of 110 as byte to file.
                outFile.writeBoolean(true); // write binary representation of boolean true to file.                
                outFile.writeUTF("Hello Class from writeUTF"); // write binary representation of UTF-8 string to file.
                outFile.writeBoolean(false); // write binary representation of boolean false to file.
                outFile.writeInt(1100123); // write binary representation of 1100123 as 32 bit int to file.
                outFile.writeLong(123456789012L);// write binary representation of 123456789012 as 64 bit long to file.);
                outFile.writeFloat(3.14159265F); // write binary representation of 3.14159265 (IEEE 754 32 bit FLOAT) to file.
                outFile.writeDouble(3.14159265D); // write binary representation of 3.14159265 (IEEE 754 64 bit DOUBLE) to file.
                outFile.writeShort(28150); // write binary representation of 28150 as short int (16 bit/2 bytes) to file.
                outFile.close();
                System.out.printf("File: binary_data.dat successfully written.");                        
        }        
        catch(IOException e)
        {
            System.out.printf("Exception: %s caught%n",e.getMessage());
        }
    }
}