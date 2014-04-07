/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.packet;

import ezi.file.EziInfo;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Elwin
 */
public class EziInfoPacket implements Serializable {

    private ArrayList<EziInfo> files;

    public EziInfoPacket(ArrayList<EziInfo> files) {
        this.files = files;
    }
    
    public ArrayList<EziInfo> getFiles(){
        return this.files;
    }
}
