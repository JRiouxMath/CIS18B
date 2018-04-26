/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarywritereadobjects;

import java.io.Serializable;

public class SomeClass implements Serializable {
    private int some_int;
    private long some_long;
    private float some_float;
    private double some_double;
    private String some_string;
    
    public SomeClass()
    {
        // initialize the private member variables
        some_int=1234;
        some_long=123412341234L;
        some_float=2.718281828f;
        some_double=2.718281828;
        some_string="This is some_string";
    }

    /**
     * @return the some_int
     */
    public int getSome_int() {
        return some_int;
    }

    /**
     * @param some_int the some_int to set
     */
    public void setSome_int(int some_int) {
        this.some_int = some_int;
    }

    /**
     * @return the some_long
     */
    public long getSome_long() {
        return some_long;
    }

    /**
     * @param some_long the some_long to set
     */
    public void setSome_long(long some_long) {
        this.some_long = some_long;
    }

    /**
     * @return the some_float
     */
    public float getSome_float() {
        return some_float;
    }

    /**
     * @param some_float the some_float to set
     */
    public void setSome_float(float some_float) {
        this.some_float = some_float;
    }

    /**
     * @return the some_double
     */
    public double getSome_double() {
        return some_double;
    }

    /**
     * @param some_double the some_double to set
     */
    public void setSome_double(double some_double) {
        this.some_double = some_double;
    }

    /**
     * @return the some_string
     */
    public String getSome_string() {
        return some_string;
    }

    /**
     * @param some_string the some_string to set
     */
    public void setSome_string(String some_string) {
        this.some_string = some_string;
    }
    
    @Override
    public String toString()
    {
        return String.format("  some_int: %18d%n  some_long:%18d%n some_float:%18.8f%nsome_double:%18.8f%nsome_string: %s%n",
                some_int, some_long, some_float, some_double, some_string);
    }
}