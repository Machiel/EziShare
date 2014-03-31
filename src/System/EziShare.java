/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import Connection.Connection;
import System.EziDownload;
import Data.EziDistributor;
import Data.EziFile;

/**
 *
 * @author Elwin
 */
public class EziShare{
    
    private Connection connection = null;
    private static EziDistributor packetProcessor = null;
    private String path = null;
    private int packetSize = 0;
    private int partSize = 0;
    
    public EziShare(String path, int packetSize, int partSize){
        packetProcessor = new EziDistributor();
        this.path = path;
        this.packetSize = packetSize;
        this.partSize = partSize;
        openConnections();
    }
    
    //initialize new Connection
    private void openConnections(){
        connection = new Connection(packetProcessor);
        connection.start();
        //Server ser = new Server();
        //ser.start();
        //Client c = new Client();
        //c.start();
        
    }
    
    public EziDownload download(EziFile info){
        EziDownload download = new EziDownload(info, path, packetSize, partSize);
        return download;
    }
}
