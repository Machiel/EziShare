/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eshare;

import ezi.system.EziSystem;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Elwin
 */
public class Eshare {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
     EziSystem system = new EziSystem("C:\\Users\\Elwin\\Downloads\\GrabIt Downloads", "C:\\testSpace\\ezis", 1024);
    }
}
