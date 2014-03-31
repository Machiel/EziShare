/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eshare;

import ezi.system.EziShare;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Elwin
 */
public class Eshare {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        EziShare ezShare = new EziShare("C:/", 10, 1024);
    }
}
