/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iziShare;

import Data.EziPacketProcessor;
import Data.EziFile;

/**
 *
 * @author Elwin
 */
public class ShareSystem{
    
    private Connection connection = null;
    private static EziPacketProcessor packetProcessor = null;
    private String path = null;
    private int packetSize = 0;
    private int partSize = 0;
    
    public ShareSystem(String path, int packetSize, int partSize){
        packetProcessor = new EziPacketProcessor();
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
