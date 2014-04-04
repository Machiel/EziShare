/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ezi.file;

import java.io.Serializable;

/**
 *
 * @author Elwin
 */
public class EziFileLocation implements Serializable{
    
    private String path;
    private String fileName;
    
    protected EziFileLocation(String path, String fileName){
        this.path = path;
        this.fileName = fileName;
    }

    protected String getPath() {
        return path;
    }

    protected void setPath(String path) {
        this.path = path;
    }

    protected String getFileName() {
        return fileName;
    }

    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    protected String getLocation(){
        return path+"\\"+fileName;
    }
}
