/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.system;

import ezi.connection.Connection;
import ezi.system.EziDownload;
import ezi.file.EziFile;
import ezi.file.EziFileIndexer;
import java.io.File;

/**
 *
 * @author Elwin
 */
public class EziSystem{
    
    private Connection connection = null;
    private static EziDistributor packetProcessor = null;
    private String path = null;
    private int packetSize = 0;
    
    public EziSystem(String path, int packetSize){
        //packetProcessor = new EziDistributor();
        //this.path = path;
       // this.packetSize = packetSize;
        //openConnections();;
        EziFileIndexer indexer = new EziFileIndexer(new File(path));
    }
    
    //initialize new Connection
    private void openConnections(){
        connection = new Connection(packetProcessor);
        connection.start();
    }
    
    //public EziDownload download(EziFile info){
        //EziDownload download = new EziDownload(info, path, packetSize);
        //return download;
   // }
}
