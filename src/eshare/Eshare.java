/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eshare;

import ezi.connection.EziBroadcastClient;
import ezi.connection.EziBroadCastServer;
import ezi.connection.EziClient;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class Eshare {

    public static void main(String[] args) {
        EziClient client = new EziClient();
        client.start();
        EziBroadcastClient c = new EziBroadcastClient();
        c.start();
        EziBroadCastServer b = new EziBroadCastServer();
        b.start();
    }
}
