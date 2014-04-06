/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.system;

import ezi.connection.Connection;
import ezi.system.EziDownload;
import ezi.file.EziInfo;
import ezi.file.EziInfoIndexer;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Elwin
 */
public class EziSystem{
    
    private Connection connection = null;
    private EziDistributor packetProcessor = null;
    private EziInfoIndexer indexer = null;
    private String path = null;
    private int packetSize = 0;
    
    public EziSystem(String path, String eziPath, int packetSize){
        //packetProcessor = new EziDistributor();
        //this.path = path;
       // this.packetSize = packetSize;
        //openConnections();;
        //indexer = new EziInfoIndexer(new File(path), new File(eziPath));
    }
    
    //initialize new Connection
    private void openConnections(){
        connection = new Connection(packetProcessor);
        connection.start();
    }
    
    public ArrayList<EziInfo> getLocalEziFiles(){
        return indexer.getEziInfoList();
    }
    
    //public EziDownload download(EziFile info){
        //EziDownload download = new EziDownload(info, path, packetSize);
        //return download;
   // }
}
